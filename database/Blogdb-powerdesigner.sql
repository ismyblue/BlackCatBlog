/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2018/5/16 17:14:11                           */
/*==============================================================*/


drop index ind_user_id on t_category;

drop index ind_gategory_name on t_category;

drop index IND_PRIMARY on t_category;

drop table if exists t_category;

drop index ind_parent_id on t_comments;

drop index ind_user_id on t_comments;

drop index ind_post_id on t_comments;

drop index IND_PRIMARY on t_comments;

drop table if exists t_comments;

drop index ind_link_visible on t_links;

drop index ind_user_id on t_links;

drop index IND_PRIMARY on t_links;

drop table if exists t_links;

drop index ind_post_date on t_posts;

drop index ind_post_title on t_posts;

drop index ind_gategory_id on t_posts;

drop index ind_user_id on t_posts;

drop index IND_PRIMARY on t_posts;

drop table if exists t_posts;

drop index ind_user_nicename on t_users;

drop index ind_user_login on t_users;

drop index IND_PRIMARY on t_users;

drop table if exists t_users;

/*==============================================================*/
/* Table: t_category                                            */
/*==============================================================*/
create table t_category
(
   id                   int not null,
   parent_id            int,
   user_id              int not null,
   gategory_name        varchar(200) not null,
   primary key (id),
   unique key AK_uq_gategory_name (gategory_name)
);

/*==============================================================*/
/* Index: IND_PRIMARY                                           */
/*==============================================================*/
create index IND_PRIMARY on t_category
(
   id
);

/*==============================================================*/
/* Index: ind_gategory_name                                     */
/*==============================================================*/
create index ind_gategory_name on t_category
(
   gategory_name
);

/*==============================================================*/
/* Index: ind_user_id                                           */
/*==============================================================*/
create index ind_user_id on t_category
(
   user_id
);

/*==============================================================*/
/* Table: t_comments                                            */
/*==============================================================*/
create table t_comments
(
   id                   int not null,
   parent_id            int,
   post_id              int not null,
   user_id              int not null,
   comment_content      text not null,
   comment_author_email varchar(100),
   comment_author_url   varchar(200),
   comment_author_ip    varchar(100),
   comment_date         datetime default '0000-00-00 00:00:00',
   comment_visible      varchar(2) default 'Y',
   comment_delete       varchar(2) default 'N',
   primary key (id)
);

/*==============================================================*/
/* Index: IND_PRIMARY                                           */
/*==============================================================*/
create index IND_PRIMARY on t_comments
(
   id
);

/*==============================================================*/
/* Index: ind_post_id                                           */
/*==============================================================*/
create index ind_post_id on t_comments
(
   post_id
);

/*==============================================================*/
/* Index: ind_user_id                                           */
/*==============================================================*/
create index ind_user_id on t_comments
(
   user_id
);

/*==============================================================*/
/* Index: ind_parent_id                                         */
/*==============================================================*/
create index ind_parent_id on t_comments
(
   parent_id
);

/*==============================================================*/
/* Table: t_links                                               */
/*==============================================================*/
create table t_links
(
   id                   int not null,
   user_id              int not null,
   link_name            varchar(255),
   link_url             varchar(255) not null,
   link_description     varchar(255),
   link_visible         varchar(2) default 'Y',
   primary key (id),
   unique key AK_uq_link_url (link_url)
);

/*==============================================================*/
/* Index: IND_PRIMARY                                           */
/*==============================================================*/
create index IND_PRIMARY on t_links
(
   id
);

/*==============================================================*/
/* Index: ind_user_id                                           */
/*==============================================================*/
create index ind_user_id on t_links
(
   user_id
);

/*==============================================================*/
/* Index: ind_link_visible                                      */
/*==============================================================*/
create index ind_link_visible on t_links
(
   link_visible
);

/*==============================================================*/
/* Table: t_posts                                               */
/*==============================================================*/
create table t_posts
(
   id                   int not null,
   user_id              int not null,
   category_id          int not null,
   post_title           text not null,
   post_content         longtext,
   post_date            datetime default '0000-00-00 00:00:00',
   post_excerpt         text,
   post_status          varchar(20) default 'publish',
   comment_status       varchar(20) default 'open',
   post_modified        datetime default '0000-00-00 00:00:00',
   comment_count        int default 0,
   primary key (id)
);

alter table t_posts comment '文章表';

/*==============================================================*/
/* Index: IND_PRIMARY                                           */
/*==============================================================*/
create index IND_PRIMARY on t_posts
(
   id
);

/*==============================================================*/
/* Index: ind_user_id                                           */
/*==============================================================*/
create index ind_user_id on t_posts
(
   user_id
);

/*==============================================================*/
/* Index: ind_gategory_id                                       */
/*==============================================================*/
create index ind_gategory_id on t_posts
(
   category_id
);

/*==============================================================*/
/* Index: ind_post_title                                        */
/*==============================================================*/
create index ind_post_title on t_posts
(
   post_title
);

/*==============================================================*/
/* Index: ind_post_date                                         */
/*==============================================================*/
create index ind_post_date on t_posts
(
   post_date
);

/*==============================================================*/
/* Table: t_users                                               */
/*==============================================================*/
create table t_users
(
   id                   int not null auto_increment,
   user_privilege       smallint default 1,
   user_login           varchar(60) not null,
   user_pass            varchar(64) not null,
   user_nicename        varchar(60),
   user_email           varchar(100) not null,
   user_url             varchar(100),
   user_registered      datetime default '0000-00-00 00:00:00',
   user_status          varchar(60) default '0',
   site_name            varchar(250),
   login_ip             varchar(50),
   login_times          bigint,
   primary key (id),
   unique key AK_uq_name (user_login)
);

alter table t_users comment '用户表';

/*==============================================================*/
/* Index: IND_PRIMARY                                           */
/*==============================================================*/
create index IND_PRIMARY on t_users
(
   id
);

/*==============================================================*/
/* Index: ind_user_login                                        */
/*==============================================================*/
create index ind_user_login on t_users
(
   user_login
);

/*==============================================================*/
/* Index: ind_user_nicename                                     */
/*==============================================================*/
create index ind_user_nicename on t_users
(
   user_nicename
);

alter table t_category add constraint FK_parent_id foreign key (parent_id)
      references t_category (id) on delete restrict on update restrict;

alter table t_category add constraint FK_user_id foreign key (user_id)
      references t_users (id) on delete restrict on update restrict;

alter table t_comments add constraint FK_parent_id foreign key (parent_id)
      references t_comments (id) on delete restrict on update restrict;

alter table t_comments add constraint FK_post_id foreign key (post_id)
      references t_posts (id) on delete restrict on update restrict;

alter table t_comments add constraint FK_post_id foreign key (user_id)
      references t_users (id) on delete restrict on update restrict;

alter table t_links add constraint FK_user_id foreign key (user_id)
      references t_users (id) on delete restrict on update restrict;

alter table t_posts add constraint FK_gategory_id foreign key (category_id)
      references t_category (id) on delete restrict on update restrict;

alter table t_posts add constraint FK_user_id foreign key (user_id)
      references t_users (id) on delete restrict on update restrict;

