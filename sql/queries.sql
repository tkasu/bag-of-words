--COUNT DICT WORDS IN TEXTS
--TO-DO: Take dict-groupings to account
SELECT COUNT(t.word) count, neg.word neg, pos.word pos, un.word uncert
FROM release_text t
LEFT JOIN dict_words neg ON (t.word = neg.word AND neg.dict_id = 1)
LEFT JOIN dict_words pos ON (t.word = pos.word AND pos.dict_id = 2)
LEFT JOIN dict_words un ON (t.word = un.word AND un.dict_id = 3)
WHERE t.word IN (SELECT word FROM dict_words)
GROUP BY t.word, neg.word, pos.word, un.word
ORDER BY COUNT(t.word) DESC;

--COUNT TOTAL WORDS AND WORDS IN EACH DICT FOR EACH FINANCIAL RELEASE IN THE DATA SET
SELECT info.company_name, info.publish_date, COUNT(text.word) words, COUNT(neg.word) neg_words, COUNT(pos.word) pos_words, COUNT(un.word) uncert_words
FROM release_text text
LEFT JOIN release_info info ON (text.release_id = info.id)
LEFT JOIN dict_words neg ON (text.word = neg.word AND neg.dict_id = 1)
LEFT JOIN dict_words pos ON (text.word = pos.word AND pos.dict_id = 2)
LEFT JOIN dict_words un ON (text.word = un.word AND un.dict_id = 3)
GROUP BY info.company_name, info.publish_date
ORDER BY info.company_name, info.publish_date;

--COUNT DICT WORDS IN DOCUMENTS, BREAKDOWN AT A WORD LEVEL
SELECT info.company_name, info.publish_date, text.word word, COUNT(text.word),
        CASE WHEN COUNT(neg.word) > 0 THEN 1 ELSE 0 END neg_words,
        CASE WHEN COUNT(pos.word) > 0 THEN 1 ELSE 0 END pos_words,
        CASE WHEN COUNT(un.word) > 0 THEN 1 ELSE 0 END uncert_words
FROM release_text text
LEFT JOIN release_info info ON (text.release_id = info.id)
LEFT JOIN dict_words neg ON (text.word = neg.word AND neg.dict_id = 1)
LEFT JOIN dict_words pos ON (text.word = pos.word AND pos.dict_id = 2)
LEFT JOIN dict_words un ON (text.word = un.word AND un.dict_id = 3)
WHERE text.word IN (SELECT word FROM dict_words)
GROUP BY info.company_name, info.publish_date, text.word
ORDER BY info.company_name, info.publish_date, COUNT(text.word) DESC;


--IN HOW MANY DOCUMENTS WORD OCCURS
--TO-DO: Take dict-groupings to account
SELECT word, SUM(neg_words) neg_doc_occur, SUM(pos_words) pos_word_occur, SUM(uncert_words) uncert_word_occur
FROM
  (SELECT info.company_name, info.publish_date, text.word word, COUNT(text.word),
          CASE WHEN COUNT(neg.word) > 0 THEN 1 ELSE 0 END neg_words,
          CASE WHEN COUNT(pos.word) > 0 THEN 1 ELSE 0 END pos_words,
          CASE WHEN COUNT(un.word) > 0 THEN 1 ELSE 0 END uncert_words
  FROM release_text text
  LEFT JOIN release_info info ON (text.release_id = info.id)
  LEFT JOIN dict_words neg ON (text.word = neg.word AND neg.dict_id = 1)
  LEFT JOIN dict_words pos ON (text.word = pos.word AND pos.dict_id = 2)
  LEFT JOIN dict_words un ON (text.word = un.word AND un.dict_id = 3)
  WHERE text.word IN (SELECT word FROM dict_words)
  GROUP BY info.company_name, info.publish_date, text.word) tdw
GROUP BY word
ORDER BY word;


--COUNT GROUPED DICT WORDS IN DOCUMENTS, BREAKDOWN AT A WORD LEV
SELECT i.id release_id, i.company_name, i.publish_date ,g.head_word, counts.word_count,
       CASE WHEN di.id = 1 THEN 1 ELSE 0 END neg_count,
       CASE WHEN di.id = 2 THEN 1 ELSE 0 END pos_count
FROM
  (SELECT info.id info_id, grp.id group_id, dict.dict_id, COUNT(*) word_count
  FROM
    (SELECT *
     FROM release_text
     WHERE word IN (SELECT word FROM dict_words WHERE dict_id IN (1, 2))) text
      LEFT JOIN dict_words dict ON (text.word = dict.word AND dict.dict_id IN (1, 2))
      LEFT JOIN dict_group grp ON grp.id = dict.group_id
      LEFT JOIN release_info info ON (text.release_id = info.id)
  GROUP BY info.id, grp.id, dict.dict_id) counts
LEFT JOIN release_info i ON counts.info_id = i.id
LEFT JOIN dict_group g ON counts.group_id = g.id
LEFT JOIN dictionary di ON counts.dict_id = di.id
ORDER BY i.company_name, i.publish_date, counts.word_count DESC;
