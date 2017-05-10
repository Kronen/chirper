SET FOREIGN_KEY_CHECKS=0;
SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

CREATE DATABASE IF NOT EXISTS chirper_db DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE chirper_db;

DROP TABLE IF EXISTS role;
CREATE TABLE `role` (
  role_name varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO role (role_name) VALUES
('admin'),
('autorizado');

DROP TABLE IF EXISTS user;
CREATE TABLE `user` (
  user_name varchar(25) NOT NULL,
  password varchar(64) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `user` (user_name, `password`) VALUES
('admin', 'c7ad44cbad762a5da0a452f9e854fdc1e0e7a52a'),
('aweldont', '41fec74d6b0ca68c21965619ff46e326d0316ea9'),
('bdavidovicsu', 'fed1b357326c1bcd372e656286c8147af5484f0f'),
('bdrury1b', '4b6d63667ccfa865849dc446d9ebecb506845016'),
('bkidgello', 'eecb14cc415ab8689314e7efa20a4387787daf43'),
('blathee1d', '71ca18e5107b74e9468b89bc920e41bb75258d12'),
('bpardyn', '122f9c158fbb3f9313ec0346465d541abba53ad4'),
('ckilty15', '72344218403b7de78f558c43234febdace5bc943'),
('clethardyp', 'e9eced6c9c816aeab0cf1399a76547621e9cb8bf'),
('crosberg8', 'f314093befc87bf7cf56f0f338de6810c028b736'),
('dbennison9', 'a65aad993fcce27e0d1a224600c3ca157dde8e36'),
('dchoulesm', 'ad38cc678f5beda93c873174892ccd2265845401'),
('diddendena', '9f2c4ee5cae147c5c83054255d5686926c3593e7'),
('dmountstephen4', 'f077098819356e198bd0323f4c96dcf4433642ad'),
('dskewis7', 'cc754bbf45725386dbf511bc441bfd4ace241572'),
('ehardesk', '01d42aad1004e741bae1098b729076e69451e48d'),
('fjoiner19', 'b16495111c8574d2483aff9e99e86e0350332b49'),
('ggrinawayw', 'da3d6e712eb1c5a8504d6f479ecaf3e3389f2cc7'),
('gwoolrich17', '470134de85a5e7fafd4a770b97118d8fb8a3c0ec'),
('hbampfieldh', '2c8880d7736561dbb4bcfc375f6f6e89e79f1834'),
('jmcvee1', 'f55254391f98752b016499259eed71da769caf09'),
('jsturleyq', 'dcc89923c47b5a748e0aa6501273507ee2147303'),
('kbaff14', '62b2ee6a175d0d06c2bf723d40847e8d184c4e2f'),
('kronen', 'cbbd65d1f05fd994d8f3e8a5479777535e0ff299'),
('ksimkovichb', 'd3629ed7b9c7dd33a3f5b66e48bcc76daa2143f6'),
('lgayton18', '01b3ca0710d9e4fe347ba1233936ac68926ee04c'),
('lgoldby11', 'a7cad65e755d4f62d9aab808a177a25fce1a40aa'),
('lvosseg', '69d262e91b507a8a44c1ea46618043dd6842a71a'),
('mcracknallz', '7db672e9d211ddcee3b196da2ab884c8e4bf6969'),
('mmorefieldv', '09ef752165fa51121312730bbcf0ed1009364949'),
('mpountney2', '1e26e05a65883b190247a69e1c9939b53102bba1'),
('mpoxon10', '6060a4f040e147b5374c2f0ca3a5d3c9ce00028d'),
('myepiskopov0', 'c82cb47d521b417599fca5d1312b2fd0960eba19'),
('nbernardot16', 'de14474fd336b9839eb2ad452bb779a4f3e7165a'),
('ncornilll', '4d34d6f6d851b2a17432f632b2a5bfb2e14759c0'),
('nsabey13', '4cfecf4be7fde0da353389959052fa7f6c964926'),
('pblewmenc', 'f1a4082861a36ffd8fa5d0c2382ace6acfafe71c'),
('pcovills', '376163a05b73a7d0aca4603004bc80ad11e2ce18'),
('pvarnsf', '30f6c7b35a4b66866532738f90bd02aa51d3adbd'),
('pwoodruffed', '5fdbc254aa7c6367128f2c449de745b2540684f3'),
('qmcgeaney1c', '6472304a301d27fccac2dcdb2935cffdc7095e2b'),
('rmurkus5', '7f6e7e1cd6bce1c8db73c9867ed310ce57f239a6'),
('rvaillanty', '8ce2d3d4dc38f0d57afb340fc9db66fa46cdd4fd'),
('sciciari3', '4265bcf647f7b56d98ead9a731c719eec43ea479'),
('scirlosj', 'eb272ec38ee3f52629cbbf83139189cdc2ec88a4'),
('scodringtone', '274ac62f03ad4e0b4050260ce8e6fe882e4da7d8'),
('sdu1a', '8ff706d37d856d161b67ea9cf9010aa59c12a69f'),
('tbloorer', '4438445f9c3cdede0d6b446bd45ab184b410ff51'),
('test', 'ee26b0dd4af7e749aa1a8ee3c10ae9923f618980'),
('tmanfordx', 'cba9b13231ba6f7f7d141b53aa7da7c0cda50569'),
('vjedrysik6', '49b9505a541fd3793a4f1614fedc986bf1ebfae0'),
('wlipyeati', '822ecb6483e263b29e761323b87307b02606e8d0'),
('wtopley12', '632b2e261f9a6c3daee2e41cb6844177cfe48a4d');

DROP TABLE IF EXISTS user_role;
CREATE TABLE user_role (
  user_name varchar(25) NOT NULL,
  role_name varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO user_role (user_name, role_name) VALUES
('admin', 'admin'),
('test', 'autorizado');

DROP TABLE IF EXISTS `profile`;
CREATE TABLE `profile` (
  id int(11) NOT NULL,
  user varchar(25) NOT NULL,
  email varchar(256) DEFAULT NULL,
  location varchar(40) DEFAULT NULL,
  website varchar(256) DEFAULT NULL,
  full_name varchar(60) DEFAULT NULL,
  num_posts int(11) NOT NULL DEFAULT '0',
  photo varchar(25) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `profile` (id, user, email, location, website, num_posts, full_name, photo) VALUES
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


DROP TABLE IF EXISTS follower_followee;
CREATE TABLE follower_followee (
  follower int(11) NOT NULL,
  followee int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO follower_followee (follower, followee) VALUES
(2, 1);

DROP TABLE IF EXISTS post;
CREATE TABLE post (
  id int(11) NOT NULL,
  author int(11) NOT NULL,
  original_post int(11) DEFAULT NULL,
  text varchar(256) NOT NULL,
  pub_date datetime NOT NULL,
  latitude decimal(9,6) DEFAULT NULL,
  longitude decimal(9,6) DEFAULT NULL,
  likes int(11) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO post (id, author, original_post, `text`, pub_date, latitude, longitude, likes) VALUES
(1, 1, NULL, 'Esto es un mensaje', '2017-03-26 00:00:00', NULL, NULL, 0),
(2, 1, NULL, 'Este fue el primer post de todos los tiempos.', '2017-03-25 00:00:00', NULL, NULL, 0),
(3, 1, NULL, 'Primer post creado dinámicamente.', '2017-05-07 23:48:43', '36.725047', '-4.452898', 0),
(4, 1, NULL, 'Segundo Post dinámico.', '2017-05-08 00:18:49', '36.725067', '-4.452899', 0),
(5, 1, NULL, 'Otra prueba mas', '2017-05-08 00:23:42', '36.725084', '-4.452899', 0);

DROP TABLE IF EXISTS private;
CREATE TABLE private (
  id int(11) NOT NULL,
  sender int(11) NOT NULL,
  receiver int(11) NOT NULL,
  text varchar(256) NOT NULL,
  marked_read tinyint(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


DROP TABLE IF EXISTS tag;
CREATE TABLE tag (
  id int(11) NOT NULL,
  tag_name varchar(256) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS post_tag;
CREATE TABLE post_tag (
  post int(11) NOT NULL,
  tag int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


DROP VIEW IF EXISTS `post_followees`;
CREATE ALGORITHM=UNDEFINED DEFINER=proyecto@localhost SQL SECURITY DEFINER VIEW chirper_db.post_followees  AS  (
  select uuid() AS `id`,p1.`id` AS id_follower,p1.user AS follower,p2.user AS followee,p.`text` AS `text`,
    p.original_post AS original_post,p.pub_date AS pub_date,p.latitude AS latitude,p.longitude AS longitude,p.likes AS likes 
    from (((`chirper_db`.`profile` p1 
      join `chirper_db`.follower_followee f on((p1.`id` = f.follower))) 
      join `chirper_db`.`profile` p2 on((p2.`id` = f.followee))) 
      join `chirper_db`.post p on((p.author = p2.`id`))));


ALTER TABLE `user`
  ADD PRIMARY KEY (user_name);

ALTER TABLE user_role
  ADD PRIMARY KEY (user_name,role_name);

ALTER TABLE role
  ADD PRIMARY KEY (role_name);

ALTER TABLE follower_followee
  ADD PRIMARY KEY (follower,followee),
  ADD KEY followee (followee);

ALTER TABLE post
  ADD PRIMARY KEY (id),
  ADD KEY author (author);

ALTER TABLE private
  ADD PRIMARY KEY (id),
  ADD KEY sender (sender),
  ADD KEY receiver (receiver);

ALTER TABLE `profile`
  ADD PRIMARY KEY (id),
  ADD KEY user (user);

ALTER TABLE tag
  ADD PRIMARY KEY (id);

ALTER TABLE post_tag
  ADD PRIMARY KEY (post,tag),
  ADD KEY tag (tag);


ALTER TABLE post
  MODIFY id int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
ALTER TABLE private
  MODIFY id int(11) NOT NULL AUTO_INCREMENT;
ALTER TABLE `profile`
  MODIFY id int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=102;
ALTER TABLE tag
  MODIFY id int(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE follower_followee
  ADD CONSTRAINT followee_ibfk_2 FOREIGN KEY (followee) REFERENCES profile (id),
  ADD CONSTRAINT follower_ibfk_1 FOREIGN KEY (follower) REFERENCES profile (id);

ALTER TABLE post
  ADD CONSTRAINT post_ibfk_1 FOREIGN KEY (author) REFERENCES profile (id) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE private
  ADD CONSTRAINT private_ibfk_1 FOREIGN KEY (sender) REFERENCES profile (id),
  ADD CONSTRAINT private_ibfk_2 FOREIGN KEY (receiver) REFERENCES profile (id);

ALTER TABLE `profile`
  ADD CONSTRAINT profile_ibfk_1 FOREIGN KEY (user) REFERENCES `user` (user_name) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE post_tag
  ADD CONSTRAINT tag_ibfk_1 FOREIGN KEY (tag) REFERENCES tag(id),
  ADD CONSTRAINT post_ibfk_2 FOREIGN KEY (post) REFERENCES post(id);

COMMIT;

SET FOREIGN_KEY_CHECKS=1;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
