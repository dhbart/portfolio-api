WITH novo_doc AS (
    INSERT INTO knowledge.documents (
                                  id
                                ,title
                                ,"source"
                                ,"language"
                                ,file_type
                                ,original_file_name
                                ,mime_type
                                ,file_size
                                ,file_hash
                                ,created_at
                                ,ingested_at
)
VALUES (
         gen_random_uuid()
       ,'Meu segundo arquivo'
       ,'manual'
       ,'en-US'
       ,'pdf'
       ,'Meu primeiro arquivo'
       ,'application/pdf'
       ,0
       ,'4686565d460454403a472718646f14a0377a5523ecb'
       ,now()
       ,now()
       )
    RETURNING id
    )

INSERT INTO knowledge.chunks
(
    id,
    document_id,
    chunk_index,
    content
)
VALUES
    (
     gen_random_uuid(),
        (SELECT id FROM novo_doc),
        1,
        'Este é um teste 2.'
    );