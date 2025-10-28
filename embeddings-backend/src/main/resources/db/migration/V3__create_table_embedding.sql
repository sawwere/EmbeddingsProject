CREATE TABLE IF NOT EXISTS embedding (
    "question_id" BIGINT PRIMARY KEY REFERENCES questions(id) ON DELETE CASCADE,
    "model" TEXT NOT NULL,
    "embedding_vector" VECTOR(768) NOT NULL
);
