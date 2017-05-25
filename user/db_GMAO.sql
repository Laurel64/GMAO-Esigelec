
CREATE TABLE Devis_dev(
dev_id NUMBER(3) NOT NULL,
dev_montant FLOAT(10),
dev_tm_id NUMBER(3),
CONSTRAINT pk_dev_id PRIMARY KEY(dev_id),
CONSTRAINT fk_dev_tm_id FOREIGN KEY(dev_tm_id) REFERENCES TicketMaintenance_tm(tm_id) ON DELETE CASCADE
);

CREATE TABLE TicketMaintenance_tm(
tm_id NUMBER(3) NOT NULL, 
tm_nom VARCHAR2(32) NOT NULL,
tm_type VARCHAR2(100),
tm_dateCreation DATE DEFAULT SYSDATE, 
tm_duree VARCHAR2(32),
tm_isClassified NUMBER(1),
tm_description VARCHAR2(1000),
tm_cl_id NUMBER(3),
CONSTRAINT pk_tm_id PRIMARY KEY(tm_id),
CONSTRAINT fk_tm_cl_id FOREIGN KEY(tm_cl_id) REFERENCES Client_cl(cl_id) ON DELETE CASCADE 
);

CREATE TABLE Operateur_op(
op_id NUMBER(3) NOT NULL,
op_nom VARCHAR2(32) NOT NULL,
op_prenom VARCHAR2(32) NOT NULL,
op_adresse VARCHAR2(60),
op_numTel NUMBER(10),
op_specialite VARCHAR2(32),
CONSTRAINT pk_id PRIMARY KEY(op_id)
);

CREATE TABLE OperaTicket_ot(
ot_id_tm NUMBER(3),
ot_id_op NUMBER(3),
CONSTRAINT fk_ot_id_tm FOREIGN KEY(ot_id_tm) REFERENCES TicketMaintenance_tm(tm_id) ON DELETE SET NULL,
CONSTRAINT fk_ot_id_op FOREIGN KEY(ot_id_op) REFERENCES Operateur_op(op_id) ON DELETE SET NULL
);


CREATE TABLE Responsable_respo(
respo_id NUMBER(3) NOT NULL,
respo_nom VARCHAR2(32) NOT NULL,
respo_prenom VARCHAR2(32) NOT NULL,
respo_adresse VARCHAR2(60),
respo_numTel NUMBER(10),
CONSTRAINT pk_respo_id PRIMARY KEY(respo_id)
);

CREATE TABLE Client_cl(
cl_id NUMBER(3) NOT NULL,
cl_nom VARCHAR2(32) NOT NULL,
cl_numeroSiret VARCHAR2(32) NOT NULL,
cl_adresse VARCHAR2(60),
cl_codeAPE VARCHAR2(32),
cl_dateCreation DATE DEFAULT SYSDATE,
cl_numTel NUMBER(10),
CONSTRAINT pk_cl_id PRIMARY KEY(cl_id)
);

CREATE TABLE ClientTicket_ct(
ct_id_tm NUMBER(3),
ct_id_cl NUMBER(3),
CONSTRAINT fk_ct_id_tm FOREIGN KEY(ct_id_tm) REFERENCES TicketMaintenance_tm(tm_id) ON DELETE SET NULL,
CONSTRAINT fk_ct_id_cl FOREIGN KEY(ct_id_cl) REFERENCES Client_cl(cl_id) ON DELETE CASCADE 
);

CREATE TABLE CompteRendu_cr(
cr_id NUMBER(3) NOT NULL,
cr_description VARCHAR2(60),
cr_date DATE DEFAULT SYSDATE,
cr_tm_id NUMBER(3),
CONSTRAINT pk_cr_id PRIMARY KEY(cr_id),
CONSTRAINT fk_cr_tm_id FOREIGN KEY(cr_tm_id) REFERENCES TicketMaintenance_tm(tm_id) ON DELETE CASCADE 
);

CREATE TABLE Profil_pro(
pro_id NUMBER(3),
pro_profil VARCHAR2(32),
CONSTRAINT pk_pro_id PRIMARY KEY(pro_id)
);

CREATE TABLE Utilisateur_u(
u_id NUMBER(3)NOT NULL,
u_login VARCHAR2(32) NOT NULL,
u_password VARCHAR2(32) NOT NULL,
u_pro_id NUMBER(3) NOT NULL,
CONSTRAINT pk_u_id PRIMARY KEY(u_id),
CONSTRAINT fk_u_pro_id FOREIGN KEY(u_pro_id) REFERENCES Profil_pro(pro_id) ON DELETE SET NULL
);
