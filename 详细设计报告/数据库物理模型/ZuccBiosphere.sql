/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2021/11/22 20:55:16                          */
/*==============================================================*/


drop table if exists ACFCRecord;

drop table if exists Admin;

drop table if exists AnimalCityFriendsClub;

drop table if exists Browse;

drop table if exists CommentRecord;

drop table if exists FavoriteRecord;

drop table if exists OperationRecord;

drop table if exists PopularizationOfScience;

drop table if exists Post;

drop table if exists ReportRecord;

drop table if exists User;

/*==============================================================*/
/* Table: ACFCRecord                                            */
/*==============================================================*/
create table ACFCRecord
(
   user_ID              varchar(20) not null,
   activity_ID          bigint not null,
   ACFCR_JoinDate       date,
   primary key (user_ID, activity_ID)
);

/*==============================================================*/
/* Table: Admin                                                 */
/*==============================================================*/
create table Admin
(
   admin_ID             varchar(20) not null,
   admin_Password       varchar(20),
   primary key (admin_ID)
);

/*==============================================================*/
/* Table: AnimalCityFriendsClub                                 */
/*==============================================================*/
create table AnimalCityFriendsClub
(
   activity_ID          bigint not null,
   admin_ID             varchar(20),
   user_ID              varchar(20),
   activity_Type        varchar(20),
   activity_ParticipantsNum varchar(20),
   activity_Content     varchar(100),
   activity_Location    varchar(100),
   activity_Date        datetime,
   activity_Image       longblob,
   primary key (activity_ID)
);

/*==============================================================*/
/* Table: Browse                                                */
/*==============================================================*/
create table Browse
(
   user_ID              varchar(20) not null,
   pos_ID               bigint not null,
   primary key (user_ID, pos_ID)
);

/*==============================================================*/
/* Table: CommentRecord                                         */
/*==============================================================*/
create table CommentRecord
(
   user_ID              varchar(20) not null,
   post_ID              bigint not null,
   Comment_Date         datetime,
   Comment_Con          varchar(500),
   Comment_Acced        varchar(20),
   primary key (user_ID, post_ID)
);

/*==============================================================*/
/* Table: FavoriteRecord                                        */
/*==============================================================*/
create table FavoriteRecord
(
   user_ID              varchar(20) not null,
   post_ID              bigint not null,
   FaRec_Acced          varchar(20),
   FaRec_Date           datetime,
   primary key (user_ID, post_ID)
);

/*==============================================================*/
/* Table: OperationRecord                                       */
/*==============================================================*/
create table OperationRecord
(
   Ope_ID               bigint not null,
   admin_ID             varchar(20),
   Ope_Rec              varchar(100),
   Ope_Date             datetime,
   primary key (Ope_ID)
);

/*==============================================================*/
/* Table: PopularizationOfScience                               */
/*==============================================================*/
create table PopularizationOfScience
(
   pos_ID               bigint not null,
   admin_ID             varchar(20),
   pos_Type             varchar(20),
   pos_Name             varchar(20),
   pos_NickName         varchar(20),
   pos_Character        varchar(20),
   pos_Sex              varchar(20),
   pos_Condition        varchar(100),
   pos_Appearance       varchar(100),
   pos_Location         varchar(100),
   pos_Image            longblob,
   primary key (pos_ID)
);

/*==============================================================*/
/* Table: Post                                                  */
/*==============================================================*/
create table Post
(
   post_ID              bigint not null,
   user_ID              varchar(20),
   post_Content         varchar(20),
   post_Date            datetime,
   post_LikeNum         bigint,
   post_CommentNum      bigint,
   post_Image           longblob,
   post_ReportNum       bigint,
   post_isTop           bool,
   post_isEssential     bool,
   primary key (post_ID)
);

/*==============================================================*/
/* Table: ReportRecord                                          */
/*==============================================================*/
create table ReportRecord
(
   user_ID              varchar(20) not null,
   post_ID              bigint not null,
   Rep_Con              varchar(500),
   Rep_Date             datetime,
   primary key (user_ID, post_ID)
);

/*==============================================================*/
/* Table: User                                                  */
/*==============================================================*/
create table User
(
   user_ID              varchar(20) not null,
   user_Name            varchar(20),
   user_Password        varchar(20),
   user_Phone           varchar(20),
   user_Gender          varchar(20),
   primary key (user_ID)
);

alter table ACFCRecord add constraint FK_ACFCRecord foreign key (user_ID)
      references User (user_ID) on delete restrict on update restrict;

alter table ACFCRecord add constraint FK_ACFCRecord2 foreign key (activity_ID)
      references AnimalCityFriendsClub (activity_ID) on delete restrict on update restrict;

alter table AnimalCityFriendsClub add constraint FK_Release2 foreign key (user_ID)
      references User (user_ID) on delete restrict on update restrict;

alter table AnimalCityFriendsClub add constraint FK_Review2 foreign key (admin_ID)
      references Admin (admin_ID) on delete restrict on update restrict;

alter table Browse add constraint FK_Browse foreign key (user_ID)
      references User (user_ID) on delete restrict on update restrict;

alter table Browse add constraint FK_Browse2 foreign key (pos_ID)
      references PopularizationOfScience (pos_ID) on delete restrict on update restrict;

alter table CommentRecord add constraint FK_CommentRecord foreign key (user_ID)
      references User (user_ID) on delete restrict on update restrict;

alter table CommentRecord add constraint FK_CommentRecord2 foreign key (post_ID)
      references Post (post_ID) on delete restrict on update restrict;

alter table FavoriteRecord add constraint FK_FavoriteRecord foreign key (user_ID)
      references User (user_ID) on delete restrict on update restrict;

alter table FavoriteRecord add constraint FK_FavoriteRecord2 foreign key (post_ID)
      references Post (post_ID) on delete restrict on update restrict;

alter table OperationRecord add constraint FK_add foreign key (admin_ID)
      references Admin (admin_ID) on delete restrict on update restrict;

alter table PopularizationOfScience add constraint FK_Review foreign key (admin_ID)
      references Admin (admin_ID) on delete restrict on update restrict;

alter table Post add constraint FK_Release foreign key (user_ID)
      references User (user_ID) on delete restrict on update restrict;

alter table ReportRecord add constraint FK_ReportRecord foreign key (user_ID)
      references User (user_ID) on delete restrict on update restrict;

alter table ReportRecord add constraint FK_ReportRecord2 foreign key (post_ID)
      references Post (post_ID) on delete restrict on update restrict;

