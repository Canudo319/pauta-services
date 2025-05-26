CREATE TABLE tbl_usuario(
	usuario_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
	cpf VARCHAR(11) NOT NULL UNIQUE,
	nome VARCHAR(250) NOT NULL,
	email_contato VARCHAR(60) NOT NULL UNIQUE,
	data_criacao DATETIME DEFAULT (datetime('now', 'localtime')),
	ativo boolean DEFAULT TRUE CHECK(ativo = 0 OR ativo = 1),
	data_alteracao DATETIME NOT NULL DEFAULT (datetime('now', 'localtime'))
);

CREATE TABLE tbl_pauta(
	pauta_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
	autor INTEGER NOT NULL REFERENCES tbl_usuario(usuario_id),
	titulo_pauta VARCHAR(50) NOT NULL,
	descricao_pauta VARCHAR(255) NOT NULL,
	data_criacao DATETIME NOT NULL DEFAULT (datetime('now', 'localtime')),
	data_abertura DATETIME NOT NULL,
	data_encerramento DATETIME NOT NULL,
	data_alteracao DATETIME NOT NULL DEFAULT (datetime('now', 'localtime'))
);

CREATE TABLE tbl_votos_pauta(
	votos_pauta_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
	pauta_id INTEGER NOT NULL REFERENCES tbl_pauta(pauta_id),
	associado INTEGER NOT NULL REFERENCES tbl_usuario(usuario_id),
	voto VARCHAR(1) NOT NULL CHECK(voto = 'N' OR voto = 'S'),
	data_criacao DATETIME NOT NULL DEFAULT (datetime('now', 'localtime')),
	data_alteracao DATETIME NOT NULL DEFAULT (datetime('now', 'localtime')),
	UNIQUE(pauta_id, associado)
);