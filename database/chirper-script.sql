SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

CREATE DATABASE IF NOT EXISTS proyect_chirper DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE proyect_chirper;

CREATE TABLE `profile` (
  id int(11) NOT NULL,
  user_name varchar(25) NOT NULL,
  email varchar(256) DEFAULT NULL,
  location varchar(40) DEFAULT NULL,
  website varchar(256) DEFAULT NULL,
  num_posts int(11) DEFAULT '0',
  full_name varchar(60) DEFAULT NULL,
  photo varchar(25) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `profile` (id, user_name, email, location, website, num_posts, full_name, photo) VALUES
(1, 'test', 'test@test.com', 'Malaga', NULL, 1, 'Test User', NULL),
(2, 'kronen', 'kronenr@gmail.com', 'España', 'http://kronen.github.io', 0, 'Alberto G. Lagos', 'kronen.jpg'),
(52, 'myepiskopov0', 'myepiskopov0@shinystat.com', 'myepiskopov0@dropbox.com', 'https://pbs.org/nisi/at/nibh/in/hac/habitasse/platea.jpg', 0, 'Maudie Yepiskopov', 'ac_tellus_semper.tiff'),
(53, 'jmcvee1', 'jmcvee1@discuz.net', 'jmcvee1@drupal.org', 'https://meetup.com/enim/lorem/ipsum.html', 0, 'Joey McVee', 'tristique.jpeg'),
(54, 'mpountney2', 'mpountney2@e-recht24.de', 'mpountney2@yandex.ru', 'https://wsj.com/nec/sem/duis/aliquam/convallis.xml', 0, 'Melosa Pountney', 'phasellus.tiff'),
(55, 'sciciari3', 'sciciari3@narod.ru', 'sciciari3@squarespace.com', 'http://alibaba.com/sed/vestibulum.xml', 0, 'Simone Ciciari', 'quisque_erat_eros.tiff'),
(56, 'dmountstephen4', 'dmountstephen4@cnn.com', 'dmountstephen4@pen.io', 'http://tmall.com/nullam/sit/amet/turpis/elementum/ligula/vehicula.jsp', 0, 'Dirk Mountstephen', 'amet_sapien_dignissim.tif'),
(57, 'rmurkus5', 'rmurkus5@auda.org.au', 'rmurkus5@statcounter.com', 'https://opensource.org/sem/praesent.json', 0, 'Rory Murkus', 'vestibulum.png'),
(58, 'vjedrysik6', 'vjedrysik6@google.com.br', 'vjedrysik6@nymag.com', 'https://home.pl/in/libero/ut/massa/volutpat.aspx', 0, 'Vida Jedrysik', 'sed.png'),
(59, 'dskewis7', 'dskewis7@freewebs.com', 'dskewis7@cargocollective.com', 'http://livejournal.com/semper/sapien/a/libero/nam/dui/proin.html', 0, 'Drugi Skewis', 'vel_pede_morbi.png'),
(60, 'crosberg8', 'crosberg8@is.gd', 'crosberg8@comcast.net', 'https://google.com.br/dapibus/dolor.xml', 0, 'Christal Rosberg', 'non_mi.png'),
(61, 'dbennison9', 'dbennison9@reverbnation.com', 'dbennison9@gizmodo.com', 'http://opensource.org/ornare/imperdiet.json', 0, 'Dwayne Bennison', 'suspendisse.gif'),
(62, 'diddendena', 'diddendena@walmart.com', 'diddendena@dmoz.org', 'http://dedecms.com/quis/turpis/sed.aspx', 0, 'Darb Iddenden', 'aliquet_ultrices_erat.gif'),
(63, 'ksimkovichb', 'ksimkovichb@dailymotion.com', 'ksimkovichb@census.gov', 'https://hexun.com/scelerisque/quam.jsp', 0, 'Kerr Simkovich', 'sit_amet.gif'),
(64, 'pblewmenc', 'pblewmenc@aol.com', 'pblewmenc@cbc.ca', 'http://baidu.com/in/tempor/turpis/nec/euismod.png', 0, 'Pete Blewmen', 'molestie.jpeg'),
(65, 'pwoodruffed', 'pwoodruffed@nydailynews.com', 'pwoodruffed@typepad.com', 'http://reddit.com/orci/luctus/et/ultrices/posuere.aspx', 0, 'Patrice Woodruffe', 'in.tiff'),
(66, 'scodringtone', 'scodringtone@google.ru', 'scodringtone@berkeley.edu', 'https://nymag.com/tempus/vel/pede/morbi/porttitor/lorem.js', 0, 'Sibbie Codrington', 'cursus.jpeg'),
(67, 'pvarnsf', 'pvarnsf@newsvine.com', 'pvarnsf@nymag.com', 'http://ed.gov/at/lorem/integer/tincidunt/ante.aspx', 0, 'Philip Varns', 'lobortis_ligula.png'),
(68, 'lvosseg', 'lvosseg@ibm.com', 'lvosseg@tinypic.com', 'http://rediff.com/faucibus/cursus/urna/ut.png', 0, 'Lisbeth Vosse', 'fringilla.jpeg'),
(69, 'hbampfieldh', 'hbampfieldh@yellowpages.com', 'hbampfieldh@twitpic.com', 'http://fc2.com/eget/eros.html', 0, 'Hodge Bampfield', 'amet.tiff'),
(70, 'wlipyeati', 'wlipyeati@mit.edu', 'wlipyeati@state.tx.us', 'http://shop-pro.jp/amet/consectetuer/adipiscing/elit.json', 0, 'Willabella Lipyeat', 'faucibus.png'),
(71, 'scirlosj', 'scirlosj@ca.gov', 'scirlosj@apache.org', 'https://google.com.au/libero/nullam/sit/amet/turpis/elementum.aspx', 0, 'Sander Cirlos', 'dui_nec_nisi.jpeg'),
(72, 'ehardesk', 'ehardesk@si.edu', 'ehardesk@elegantthemes.com', 'http://ihg.com/nulla/ac/enim/in/tempor.js', 0, 'Eleni Hardes', 'leo_odio_condimentum.gif'),
(73, 'ncornilll', 'ncornilll@imdb.com', 'ncornilll@slate.com', 'http://disqus.com/augue.js', 0, 'Nessie Cornill', 'pede.tiff'),
(74, 'dchoulesm', 'dchoulesm@edublogs.org', 'dchoulesm@bloglines.com', 'https://nsw.gov.au/odio.jpg', 0, 'Demetri Choules', 'sed_accumsan_felis.gif'),
(75, 'bpardyn', 'bpardyn@ox.ac.uk', 'bpardyn@theglobeandmail.com', 'https://51.la/in/consequat/ut/nulla/sed/accumsan.jsp', 0, 'Bevvy Pardy', 'duis_aliquam_convallis.pn'),
(76, 'bkidgello', 'bkidgello@newsvine.com', 'bkidgello@sitemeter.com', 'http://eepurl.com/nulla/mollis/molestie/lorem.json', 0, 'Byran Kidgell', 'felis.png'),
(77, 'clethardyp', 'clethardyp@geocities.jp', 'clethardyp@smugmug.com', 'https://mediafire.com/scelerisque/quam/turpis/adipiscing.xml', 0, 'Celka Lethardy', 'vel_est.jpeg'),
(78, 'jsturleyq', 'jsturleyq@wordpress.com', 'jsturleyq@bloomberg.com', 'https://census.gov/dis/parturient/montes/nascetur/ridiculus.html', 0, 'Janina Sturley', 'augue.tiff'),
(79, 'tbloorer', 'tbloorer@youtu.be', 'tbloorer@ovh.net', 'https://sphinn.com/proin/eu/mi/nulla/ac/enim/in.xml', 0, 'Tomkin Bloore', 'quam.tiff'),
(80, 'pcovills', 'pcovills@ebay.co.uk', 'pcovills@baidu.com', 'http://icq.com/tellus.aspx', 0, 'Pooh Covill', 'ut_erat.tiff'),
(81, 'aweldont', 'aweldont@marriott.com', 'aweldont@fastcompany.com', 'https://sina.com.cn/mus/vivamus/vestibulum/sagittis/sapien/cum.jpg', 0, 'Abra Weldon', 'accumsan_tortor.gif'),
(82, 'bdavidovicsu', 'bdavidovicsu@sfgate.com', 'bdavidovicsu@businessweek.com', 'https://upenn.edu/nibh/quisque/id/justo/sit/amet.json', 0, 'Beryl Davidovics', 'et_ultrices.jpeg'),
(83, 'mmorefieldv', 'mmorefieldv@epa.gov', 'mmorefieldv@answers.com', 'http://examiner.com/est.aspx', 0, 'Martie Morefield', 'tortor_sollicitudin.jpeg'),
(84, 'ggrinawayw', 'ggrinawayw@studiopress.com', 'ggrinawayw@adobe.com', 'http://stumbleupon.com/ut/dolor/morbi/vel/lectus/in.aspx', 0, 'Gaby Grinaway', 'nascetur.jpeg'),
(85, 'tmanfordx', 'tmanfordx@huffingtonpost.com', 'tmanfordx@nationalgeographic.com', 'http://oakley.com/in/hac/habitasse.json', 0, 'Tammy Manford', 'maecenas.tiff'),
(86, 'rvaillanty', 'rvaillanty@biblegateway.com', 'rvaillanty@t.co', 'https://jiathis.com/sed/lacus/morbi.json', 0, 'Rose Vaillant', 'in_hac_habitasse.jpeg'),
(87, 'mcracknallz', 'mcracknallz@gizmodo.com', 'mcracknallz@etsy.com', 'http://jalbum.net/in/consequat/ut.json', 0, 'Marillin Cracknall', 'primis_in.jpeg'),
(88, 'mpoxon10', 'mpoxon10@cmu.edu', 'mpoxon10@accuweather.com', 'https://so-net.ne.jp/in/felis/donec/semper/sapien.html', 0, 'Murielle Poxon', 'habitasse_platea.jpeg'),
(89, 'lgoldby11', 'lgoldby11@dailymotion.com', 'lgoldby11@sourceforge.net', 'https://youku.com/lorem/ipsum/dolor/sit/amet/consectetuer.html', 0, 'Loree Goldby', 'amet.png'),
(90, 'wtopley12', 'wtopley12@webs.com', 'wtopley12@wikia.com', 'http://pcworld.com/proin/eu/mi.jpg', 0, 'Webb Topley', 'blandit.jpeg'),
(91, 'nsabey13', 'nsabey13@artisteer.com', 'nsabey13@t-online.de', 'https://devhub.com/in.jsp', 0, 'Niall Sabey', 'convallis.tiff'),
(92, 'kbaff14', 'kbaff14@dagondesign.com', 'kbaff14@tripod.com', 'https://fda.gov/aliquam/lacus/morbi/quis/tortor/id/nulla.xml', 0, 'Kirsten Baff', 'vestibulum_sed_magna.jpeg'),
(93, 'ckilty15', 'ckilty15@barnesandnoble.com', 'ckilty15@biglobe.ne.jp', 'http://quantcast.com/bibendum/morbi/non/quam/nec/dui.png', 0, 'Candide Kilty', 'duis_ac_nibh.tiff'),
(94, 'nbernardot16', 'nbernardot16@dailymail.co.uk', 'nbernardot16@sakura.ne.jp', 'https://wp.com/justo/sollicitudin/ut.html', 0, 'Ninnette Bernardot', 'quisque_id.gif'),
(95, 'gwoolrich17', 'gwoolrich17@dropbox.com', 'gwoolrich17@dropbox.com', 'https://springer.com/nulla/nunc/purus/phasellus/in/felis/donec.png', 0, 'Guthrey Woolrich', 'enim_lorem.jpeg'),
(96, 'lgayton18', 'lgayton18@360.cn', 'lgayton18@pbs.org', 'http://shareasale.com/eu/tincidunt.jsp', 0, 'Leandra Gayton', 'auctor_sed_tristique.png'),
(97, 'fjoiner19', 'fjoiner19@flickr.com', 'fjoiner19@plala.or.jp', 'http://jiathis.com/vel/augue.png', 0, 'Frankie Joiner', 'maecenas.png'),
(98, 'sdu1a', 'sdu1a@forbes.com', 'sdu1a@oakley.com', 'http://163.com/etiam/faucibus/cursus/urna.jpg', 0, 'Shari Du Hamel', 'sed_magna.gif'),
(99, 'bdrury1b', 'bdrury1b@ebay.co.uk', 'bdrury1b@sitemeter.com', 'https://ifeng.com/hac/habitasse/platea.html', 0, 'Brendin Drury', 'curabitur.tiff'),
(100, 'qmcgeaney1c', 'qmcgeaney1c@gravatar.com', 'qmcgeaney1c@instagram.com', 'http://opensource.org/in/hac/habitasse/platea/dictumst/etiam.jpg', 0, 'Queenie McGeaney', 'enim.png'),
(101, 'blathee1d', 'blathee1d@ebay.com', 'blathee1d@sohu.com', 'http://scientificamerican.com/pretium/quis.html', 0, 'Berny Lathee', 'dis_parturient.tiff');

CREATE TABLE post (
  id int(11) NOT NULL,
  id_author int(11) NOT NULL,
  original_post_id int(11) DEFAULT NULL,
  text varchar(256) NOT NULL,
  pub_date date NOT NULL,
  latitude decimal(9,6) DEFAULT NULL,
  longitude decimal(9,6) DEFAULT NULL,
  likes int(11) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO post (id, id_author, original_post_id, `text`, pub_date, latitude, longitude, likes) VALUES
(1, 1, NULL, 'Esto es un mensaje', '2017-03-26', NULL, NULL, 0),
(2, 1, NULL, 'Este fue el primer post de todos los tiempos.', '2017-03-25', NULL, NULL, 0);

CREATE TABLE `private` (
  id int(11) NOT NULL,
  sender_id int(11) NOT NULL,
  receiver_id int(11) NOT NULL,
  text varchar(256) NOT NULL,
  marked_read tinyint(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `role` (
  role_name varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO role (role_name) VALUES
('admin'),
('autorizado');

CREATE TABLE follower_followee (
  id_follower int(11) NOT NULL,
  id_followee int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO follower_followee (id_follower, id_followee) VALUES
(2, 1);

CREATE TABLE tag (
  id int(11) NOT NULL,
  id_post int(11) NOT NULL,
  tag varchar(256) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `user` (
  user_name varchar(25) NOT NULL,
  password varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `user` (user_name, `password`) VALUES
('admin', 'admin'),
('aweldont', 'aweldont'),
('bdavidovicsu', 'bdavidovicsu'),
('bdrury1b', 'bdrury1b'),
('bkidgello', 'bkidgello'),
('blathee1d', 'blathee1d'),
('bpardyn', 'bpardyn'),
('ckilty15', 'ckilty15'),
('clethardyp', 'clethardyp'),
('crosberg8', 'crosberg8'),
('dbennison9', 'dbennison9'),
('dchoulesm', 'dchoulesm'),
('diddendena', 'diddendena'),
('dmountstephen4', 'dmountstephen4'),
('dskewis7', 'dskewis7'),
('ehardesk', 'ehardesk'),
('fjoiner19', 'fjoiner19'),
('ggrinawayw', 'ggrinawayw'),
('gwoolrich17', 'gwoolrich17'),
('hbampfieldh', 'hbampfieldh'),
('jmcvee1', 'jmcvee1'),
('jsturleyq', 'jsturleyq'),
('kbaff14', 'kbaff14'),
('kronen', 'kronen'),
('ksimkovichb', 'ksimkovichb'),
('lgayton18', 'lgayton18'),
('lgoldby11', 'lgoldby11'),
('lvosseg', 'lvosseg'),
('mcracknallz', 'mcracknallz'),
('mmorefieldv', 'mmorefieldv'),
('mpountney2', 'mpountney2'),
('mpoxon10', 'mpoxon10'),
('myepiskopov0', 'myepiskopov0'),
('nbernardot16', 'nbernardot16'),
('ncornilll', 'ncornilll'),
('nsabey13', 'nsabey13'),
('pblewmenc', 'pblewmenc'),
('pcovills', 'pcovills'),
('pvarnsf', 'pvarnsf'),
('pwoodruffed', 'pwoodruffed'),
('qmcgeaney1c', 'qmcgeaney1c'),
('rmurkus5', 'rmurkus5'),
('rvaillanty', 'rvaillanty'),
('sciciari3', 'sciciari3'),
('scirlosj', 'scirlosj'),
('scodringtone', 'scodringtone'),
('sdu1a', 'sdu1a'),
('tbloorer', 'tbloorer'),
('test', 'test'),
('tmanfordx', 'tmanfordx'),
('vjedrysik6', 'vjedrysik6'),
('wlipyeati', 'wlipyeati'),
('wtopley12', 'wtopley12');

DROP TABLE IF EXISTS user_role;
CREATE TABLE user_role (
  user_name varchar(25) NOT NULL,
  role_name varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO user_role (user_name, role_name) VALUES
('admin', 'admin'),
('test', 'autorizado');

DROP VIEW IF EXISTS `post_followees`;
CREATE ALGORITHM=UNDEFINED DEFINER=proyecto@localhost SQL SECURITY DEFINER VIEW proyect_chirper.post_followees  AS  (
  select uuid() AS `id`, p1.`id` AS `id_follower`,p1.`user_name` AS `follower`,p2.`user_name` AS `followee`,p.`text` AS `text`,
    p.`original_post_id` AS `original_post_id`,p.`pub_date` AS `pub_date`,p.`latitude` AS `latitude`,p.`longitude` AS `longitude`,
    p.`likes` AS `likes` 
    from (((`proyect_chirper`.`profile` p1 
      join `proyect_chirper`.`follower_followee` f on((p1.`id` = f.`id_follower`))) 
      join `proyect_chirper`.`profile` p2 on((p2.`id` = f.`id_followee`))) 
      join `proyect_chirper`.`post` p on((p.`id_author` = p2.`id`)))) ;


ALTER TABLE `profile`
  ADD PRIMARY KEY (id),
  ADD UNIQUE KEY user_name (user_name);

ALTER TABLE post
  ADD PRIMARY KEY (id),
  ADD KEY id_profile (id_author);

ALTER TABLE private
  ADD PRIMARY KEY (id),
  ADD KEY sender_id (sender_id),
  ADD KEY receiver_id (receiver_id);

ALTER TABLE role
  ADD PRIMARY KEY (role_name);

ALTER TABLE follower_followee
  ADD PRIMARY KEY (id_follower,id_followee),
  ADD KEY siguiendo_ibfk_2 (id_followee);

ALTER TABLE tag
  ADD PRIMARY KEY (id),
  ADD KEY id_post (id_post);

ALTER TABLE `user`
  ADD PRIMARY KEY (user_name);

ALTER TABLE user_role
  ADD PRIMARY KEY (user_name,role_name);


ALTER TABLE `profile`
  MODIFY id int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=102;
ALTER TABLE post
  MODIFY id int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
ALTER TABLE private
  MODIFY id int(11) NOT NULL AUTO_INCREMENT;
ALTER TABLE tag
  MODIFY id int(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE `profile`
  ADD CONSTRAINT profile_ibfk_1 FOREIGN KEY (user_name) REFERENCES `user` (user_name) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE post
  ADD CONSTRAINT post_ibfk_1 FOREIGN KEY (id_author) REFERENCES `profile` (id) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE private
  ADD CONSTRAINT private_ibfk_1 FOREIGN KEY (sender_id) REFERENCES `profile` (id),
  ADD CONSTRAINT private_ibfk_2 FOREIGN KEY (receiver_id) REFERENCES `profile` (id);

ALTER TABLE follower_followee
  ADD CONSTRAINT follower_ibfk_1 FOREIGN KEY (id_follower) REFERENCES `profile` (id),
  ADD CONSTRAINT followee_ibfk_2 FOREIGN KEY (id_followee) REFERENCES `profile` (id);

ALTER TABLE tag
  ADD CONSTRAINT tag_ibfk_1 FOREIGN KEY (id_post) REFERENCES post (id) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
