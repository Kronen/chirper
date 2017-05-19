SET FOREIGN_KEY_CHECKS=0;
SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

CREATE DATABASE IF NOT EXISTS `chirper_db` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `chirper_db`;

DROP TABLE IF EXISTS `follower_followee`;
CREATE TABLE `follower_followee` (
  `follower` int(11) NOT NULL,
  `followee` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `follower_followee` (`follower`, `followee`) VALUES
(2, 1),
(2, 102),
(102, 1),
(102, 2);

DROP TABLE IF EXISTS `post`;
CREATE TABLE `post` (
  `id` int(11) NOT NULL,
  `author` int(11) NOT NULL,
  `original_post` int(11) DEFAULT NULL,
  `text` varchar(256) NOT NULL,
  `pub_date` datetime NOT NULL,
  `latitude` decimal(9,6) DEFAULT NULL,
  `longitude` decimal(9,6) DEFAULT NULL,
  `likes` int(11) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `post` (`id`, `author`, `original_post`, `text`, `pub_date`, `latitude`, `longitude`, `likes`) VALUES
(1, 1, NULL, 'Esto es un mensaje', '2017-03-26 00:00:00', NULL, NULL, 0),
(2, 1, NULL, 'Este fue el primer post de todos los tiempos.', '2017-03-25 00:00:00', NULL, NULL, 0),
(3, 1, NULL, 'Primer post creado dinámicamente.', '2017-05-07 23:48:43', '36.725047', '-4.452898', 0),
(4, 1, NULL, 'Segundo Post dinámico.', '2017-05-08 00:18:49', '36.725067', '-4.452899', 0),
(5, 1, NULL, 'Otra prueba mas', '2017-05-08 00:23:42', '36.725084', '-4.452899', 0),
(6, 2, NULL, 'Hakuna Matata!', '2017-05-15 21:26:13', '36.725082', '-4.452858', 0),
(7, 1, NULL, 'Post reciente', '2017-05-17 00:20:43', '36.725052', '-4.452892', 0),
(8, 102, NULL, 'Post de test3', '2017-05-17 20:48:00', '36.725092', '-4.452861', 0),
(12, 2, NULL, 'Bebiendo una Mahou!', '2017-05-19 18:19:59', '36.725052', '-4.452961', 0),
(14, 2, NULL, 'mi #ChirperRules', '2017-05-19 18:34:53', '36.725052', '-4.452961', 0),
(17, 2, NULL, 'mi segundo #ChirperRules', '2017-05-19 18:47:21', '36.725052', '-4.452961', 0),
(18, 2, NULL, 'el tercer #ChirperRules\r\n', '2017-05-19 19:04:02', '36.725052', '-4.452961', 0);


DROP TABLE IF EXISTS `post_tag`;
CREATE TABLE `post_tag` (
  `post` int(11) NOT NULL,
  `tag` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `post_tag` (`post`, `tag`) VALUES
(14, 4),
(17, 4),
(18, 4);

DROP TABLE IF EXISTS `private`;
CREATE TABLE `private` (
  `id` int(11) NOT NULL,
  `sender` int(11) NOT NULL,
  `receiver` int(11) NOT NULL,
  `text` varchar(256) NOT NULL,
  `marked_read` tinyint(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `profile`;
CREATE TABLE `profile` (
  `id` int(11) NOT NULL,
  `user` varchar(25) NOT NULL,
  `email` varchar(256) NOT NULL,
  `location` varchar(40) DEFAULT NULL,
  `website` varchar(256) DEFAULT NULL,
  `full_name` varchar(60) DEFAULT NULL,
  `num_posts` int(11) NOT NULL DEFAULT '0',
  `photo` varchar(25) DEFAULT NULL,
  `theme` varchar(256) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `profile` (`id`, `user`, `email`, `location`, `website`, `full_name`, `num_posts`, `photo`, `theme`) VALUES
(1, 'test', 'test@test.com', 'Malaga', NULL, 'Test User', 1, NULL, NULL),
(2, 'kronen', 'kronenr@gmail.com', 'España', 'http://kronen.github.io', 'Alberto G. Lagos', 0, 'kronen.jpg', NULL),
(52, 'myepiskopov0', 'myepiskopov0@shinystat.com', 'myepiskopov0@dropbox.com', 'https://pbs.org/nisi/at/nibh/in/hac/habitasse/platea.jpg', 'Maudie Yepiskopov', 0, 'ac_tellus_semper.tiff', NULL),
(53, 'jmcvee1', 'jmcvee1@discuz.net', 'jmcvee1@drupal.org', 'https://meetup.com/enim/lorem/ipsum.html', 'Joey McVee', 0, 'tristique.jpeg', NULL),
(54, 'mpountney2', 'mpountney2@e-recht24.de', 'mpountney2@yandex.ru', 'https://wsj.com/nec/sem/duis/aliquam/convallis.xml', 'Melosa Pountney', 0, 'phasellus.tiff', NULL),
(55, 'sciciari3', 'sciciari3@narod.ru', 'sciciari3@squarespace.com', 'http://alibaba.com/sed/vestibulum.xml', 'Simone Ciciari', 0, 'quisque_erat_eros.tiff', NULL),
(56, 'dmountstephen4', 'dmountstephen4@cnn.com', 'dmountstephen4@pen.io', 'http://tmall.com/nullam/sit/amet/turpis/elementum/ligula/vehicula.jsp', 'Dirk Mountstephen', 0, 'amet_sapien_dignissim.tif', NULL),
(57, 'rmurkus5', 'rmurkus5@auda.org.au', 'rmurkus5@statcounter.com', 'https://opensource.org/sem/praesent.json', 'Rory Murkus', 0, 'vestibulum.png', NULL),
(58, 'vjedrysik6', 'vjedrysik6@google.com.br', 'vjedrysik6@nymag.com', 'https://home.pl/in/libero/ut/massa/volutpat.aspx', 'Vida Jedrysik', 0, 'sed.png', NULL),
(59, 'dskewis7', 'dskewis7@freewebs.com', 'dskewis7@cargocollective.com', 'http://livejournal.com/semper/sapien/a/libero/nam/dui/proin.html', 'Drugi Skewis', 0, 'vel_pede_morbi.png', NULL),
(60, 'crosberg8', 'crosberg8@is.gd', 'crosberg8@comcast.net', 'https://google.com.br/dapibus/dolor.xml', 'Christal Rosberg', 0, 'non_mi.png', NULL),
(61, 'dbennison9', 'dbennison9@reverbnation.com', 'dbennison9@gizmodo.com', 'http://opensource.org/ornare/imperdiet.json', 'Dwayne Bennison', 0, 'suspendisse.gif', NULL),
(62, 'diddendena', 'diddendena@walmart.com', 'diddendena@dmoz.org', 'http://dedecms.com/quis/turpis/sed.aspx', 'Darb Iddenden', 0, 'aliquet_ultrices_erat.gif', NULL),
(63, 'ksimkovichb', 'ksimkovichb@dailymotion.com', 'ksimkovichb@census.gov', 'https://hexun.com/scelerisque/quam.jsp', 'Kerr Simkovich', 0, 'sit_amet.gif', NULL),
(64, 'pblewmenc', 'pblewmenc@aol.com', 'pblewmenc@cbc.ca', 'http://baidu.com/in/tempor/turpis/nec/euismod.png', 'Pete Blewmen', 0, 'molestie.jpeg', NULL),
(65, 'pwoodruffed', 'pwoodruffed@nydailynews.com', 'pwoodruffed@typepad.com', 'http://reddit.com/orci/luctus/et/ultrices/posuere.aspx', 'Patrice Woodruffe', 0, 'in.tiff', NULL),
(66, 'scodringtone', 'scodringtone@google.ru', 'scodringtone@berkeley.edu', 'https://nymag.com/tempus/vel/pede/morbi/porttitor/lorem.js', 'Sibbie Codrington', 0, 'cursus.jpeg', NULL),
(67, 'pvarnsf', 'pvarnsf@newsvine.com', 'pvarnsf@nymag.com', 'http://ed.gov/at/lorem/integer/tincidunt/ante.aspx', 'Philip Varns', 0, 'lobortis_ligula.png', NULL),
(68, 'lvosseg', 'lvosseg@ibm.com', 'lvosseg@tinypic.com', 'http://rediff.com/faucibus/cursus/urna/ut.png', 'Lisbeth Vosse', 0, 'fringilla.jpeg', NULL),
(69, 'hbampfieldh', 'hbampfieldh@yellowpages.com', 'hbampfieldh@twitpic.com', 'http://fc2.com/eget/eros.html', 'Hodge Bampfield', 0, 'amet.tiff', NULL),
(70, 'wlipyeati', 'wlipyeati@mit.edu', 'wlipyeati@state.tx.us', 'http://shop-pro.jp/amet/consectetuer/adipiscing/elit.json', 'Willabella Lipyeat', 0, 'faucibus.png', NULL),
(71, 'scirlosj', 'scirlosj@ca.gov', 'scirlosj@apache.org', 'https://google.com.au/libero/nullam/sit/amet/turpis/elementum.aspx', 'Sander Cirlos', 0, 'dui_nec_nisi.jpeg', NULL),
(72, 'ehardesk', 'ehardesk@si.edu', 'ehardesk@elegantthemes.com', 'http://ihg.com/nulla/ac/enim/in/tempor.js', 'Eleni Hardes', 0, 'leo_odio_condimentum.gif', NULL),
(73, 'ncornilll', 'ncornilll@imdb.com', 'ncornilll@slate.com', 'http://disqus.com/augue.js', 'Nessie Cornill', 0, 'pede.tiff', NULL),
(74, 'dchoulesm', 'dchoulesm@edublogs.org', 'dchoulesm@bloglines.com', 'https://nsw.gov.au/odio.jpg', 'Demetri Choules', 0, 'sed_accumsan_felis.gif', NULL),
(75, 'bpardyn', 'bpardyn@ox.ac.uk', 'bpardyn@theglobeandmail.com', 'https://51.la/in/consequat/ut/nulla/sed/accumsan.jsp', 'Bevvy Pardy', 0, 'duis_aliquam_convallis.pn', NULL),
(76, 'bkidgello', 'bkidgello@newsvine.com', 'bkidgello@sitemeter.com', 'http://eepurl.com/nulla/mollis/molestie/lorem.json', 'Byran Kidgell', 0, 'felis.png', NULL),
(77, 'clethardyp', 'clethardyp@geocities.jp', 'clethardyp@smugmug.com', 'https://mediafire.com/scelerisque/quam/turpis/adipiscing.xml', 'Celka Lethardy', 0, 'vel_est.jpeg', NULL),
(78, 'jsturleyq', 'jsturleyq@wordpress.com', 'jsturleyq@bloomberg.com', 'https://census.gov/dis/parturient/montes/nascetur/ridiculus.html', 'Janina Sturley', 0, 'augue.tiff', NULL),
(79, 'tbloorer', 'tbloorer@youtu.be', 'tbloorer@ovh.net', 'https://sphinn.com/proin/eu/mi/nulla/ac/enim/in.xml', 'Tomkin Bloore', 0, 'quam.tiff', NULL),
(80, 'pcovills', 'pcovills@ebay.co.uk', 'pcovills@baidu.com', 'http://icq.com/tellus.aspx', 'Pooh Covill', 0, 'ut_erat.tiff', NULL),
(81, 'aweldont', 'aweldont@marriott.com', 'aweldont@fastcompany.com', 'https://sina.com.cn/mus/vivamus/vestibulum/sagittis/sapien/cum.jpg', 'Abra Weldon', 0, 'accumsan_tortor.gif', NULL),
(82, 'bdavidovicsu', 'bdavidovicsu@sfgate.com', 'bdavidovicsu@businessweek.com', 'https://upenn.edu/nibh/quisque/id/justo/sit/amet.json', 'Beryl Davidovics', 0, 'et_ultrices.jpeg', NULL),
(83, 'mmorefieldv', 'mmorefieldv@epa.gov', 'mmorefieldv@answers.com', 'http://examiner.com/est.aspx', 'Martie Morefield', 0, 'tortor_sollicitudin.jpeg', NULL),
(84, 'ggrinawayw', 'ggrinawayw@studiopress.com', 'ggrinawayw@adobe.com', 'http://stumbleupon.com/ut/dolor/morbi/vel/lectus/in.aspx', 'Gaby Grinaway', 0, 'nascetur.jpeg', NULL),
(85, 'tmanfordx', 'tmanfordx@huffingtonpost.com', 'tmanfordx@nationalgeographic.com', 'http://oakley.com/in/hac/habitasse.json', 'Tammy Manford', 0, 'maecenas.tiff', NULL),
(86, 'rvaillanty', 'rvaillanty@biblegateway.com', 'rvaillanty@t.co', 'https://jiathis.com/sed/lacus/morbi.json', 'Rose Vaillant', 0, 'in_hac_habitasse.jpeg', NULL),
(87, 'mcracknallz', 'mcracknallz@gizmodo.com', 'mcracknallz@etsy.com', 'http://jalbum.net/in/consequat/ut.json', 'Marillin Cracknall', 0, 'primis_in.jpeg', NULL),
(88, 'mpoxon10', 'mpoxon10@cmu.edu', 'mpoxon10@accuweather.com', 'https://so-net.ne.jp/in/felis/donec/semper/sapien.html', 'Murielle Poxon', 0, 'habitasse_platea.jpeg', NULL),
(89, 'lgoldby11', 'lgoldby11@dailymotion.com', 'lgoldby11@sourceforge.net', 'https://youku.com/lorem/ipsum/dolor/sit/amet/consectetuer.html', 'Loree Goldby', 0, 'amet.png', NULL),
(90, 'wtopley12', 'wtopley12@webs.com', 'wtopley12@wikia.com', 'http://pcworld.com/proin/eu/mi.jpg', 'Webb Topley', 0, 'blandit.jpeg', NULL),
(91, 'nsabey13', 'nsabey13@artisteer.com', 'nsabey13@t-online.de', 'https://devhub.com/in.jsp', 'Niall Sabey', 0, 'convallis.tiff', NULL),
(92, 'kbaff14', 'kbaff14@dagondesign.com', 'kbaff14@tripod.com', 'https://fda.gov/aliquam/lacus/morbi/quis/tortor/id/nulla.xml', 'Kirsten Baff', 0, 'vestibulum_sed_magna.jpeg', NULL),
(93, 'ckilty15', 'ckilty15@barnesandnoble.com', 'ckilty15@biglobe.ne.jp', 'http://quantcast.com/bibendum/morbi/non/quam/nec/dui.png', 'Candide Kilty', 0, 'duis_ac_nibh.tiff', NULL),
(94, 'nbernardot16', 'nbernardot16@dailymail.co.uk', 'nbernardot16@sakura.ne.jp', 'https://wp.com/justo/sollicitudin/ut.html', 'Ninnette Bernardot', 0, 'quisque_id.gif', NULL),
(95, 'gwoolrich17', 'gwoolrich17@dropbox.com', 'gwoolrich17@dropbox.com', 'https://springer.com/nulla/nunc/purus/phasellus/in/felis/donec.png', 'Guthrey Woolrich', 0, 'enim_lorem.jpeg', NULL),
(96, 'lgayton18', 'lgayton18@360.cn', 'lgayton18@pbs.org', 'http://shareasale.com/eu/tincidunt.jsp', 'Leandra Gayton', 0, 'auctor_sed_tristique.png', NULL),
(97, 'fjoiner19', 'fjoiner19@flickr.com', 'fjoiner19@plala.or.jp', 'http://jiathis.com/vel/augue.png', 'Frankie Joiner', 0, 'maecenas.png', NULL),
(98, 'sdu1a', 'sdu1a@forbes.com', 'sdu1a@oakley.com', 'http://163.com/etiam/faucibus/cursus/urna.jpg', 'Shari Du Hamel', 0, 'sed_magna.gif', NULL),
(99, 'bdrury1b', 'bdrury1b@ebay.co.uk', 'bdrury1b@sitemeter.com', 'https://ifeng.com/hac/habitasse/platea.html', 'Brendin Drury', 0, 'curabitur.tiff', NULL),
(100, 'qmcgeaney1c', 'qmcgeaney1c@gravatar.com', 'qmcgeaney1c@instagram.com', 'http://opensource.org/in/hac/habitasse/platea/dictumst/etiam.jpg', 'Queenie McGeaney', 0, 'enim.png', NULL),
(101, 'blathee1d', 'blathee1d@ebay.com', 'blathee1d@sohu.com', 'http://scientificamerican.com/pretium/quis.html', 'Berny Lathee', 0, 'dis_parturient.tiff', NULL),
(102, 'test3', 'test3@gmail.com', NULL, NULL, NULL, 0, NULL, NULL);

DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `role_name` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_name` varchar(25) NOT NULL,
  `password` varchar(64) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `user_name` varchar(25) NOT NULL,
  `role_name` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `role` (`role_name`) VALUES
('admin'),
('autorizado');

INSERT INTO `user` (`user_name`, `password`) VALUES
('admin', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918'),
('aweldont', '56c9c31e97818eb524477058a38a59ad20cf1310d92d6f428ad30fd645a41f39'),
('bdavidovicsu', '7ed1337265d2b8d3d00723467c541aebb5cfd0ee1511fe7096fe5f40e6252dd9'),
('bdrury1b', '752d43cbe0e44b501c63e6fee770bee9cd65cf630d6f15dace5456ccebd1766c'),
('bkidgello', 'd465dc4b3dcbfca29ee7c44d810947b33fd945318b7d83bc608b3f459f1b7e3f'),
('blathee1d', '6046b3acb6134f7bfa4854ca9ec415999f7913481fba8247af4928e5d5b4f1d7'),
('bpardyn', '24e8aa9063b934f3c19df4ad6386142b4b12a16774829fc478898bebce6bdf7b'),
('ckilty15', '50bd64dac771fc0e22edfca70649decd42a1906acf95cb20fa273476f2248b14'),
('clethardyp', '707f600ead833eaf395ab4ba80bec04e047d241f2228f203dc087ce89f89000b'),
('crosberg8', '8577af0841bdf754f7af4c61c0a9361b13f06601b4e0610c74d65c2548358505'),
('dbennison9', '047e022bf811210edf7912d85a9160e37ea0df4f71cb32db79d4d105120925a3'),
('dchoulesm', '5702c8eabd639dc0be5e07dc3d4d6ae1850716c9d4b9574c4e39cf710878f386'),
('diddendena', '66339976af2673bf9aa96679e9645c734c807ff72738281a9167675a377e149c'),
('dmountstephen4', '80416d6a6e0e1662e5c03e629e38575d8f136e67c3653a8d90c3dad6d8d86496'),
('dskewis7', '2c8ef22a5398f426b251e0575294d5786f8e561607f539d1212ba61ea2b39b95'),
('ehardesk', '223201fdd47ecd06b4671b97eccb3a1fb0c7c784dc018b5b7945f42cf152d5b2'),
('fjoiner19', 'f7db827acf48447284f6a610351eca50e782ffcabae21860d35e0bde62ac1d6f'),
('ggrinawayw', 'bf659b74af5b3b9bc172f0227986bdc43168770766eaa665a1ffca858b50feac'),
('gwoolrich17', '74aff1ef450dfa79e2d293307a9638b94cabd2b8734f6001dd274f7a8d3b4c3a'),
('hbampfieldh', 'a52504aef56f72aad9435d2d6ba69ed53d030e57329a6cc9f50b2109aa50be04'),
('jmcvee1', '8f9e8d6e8f6ebf32525b5b78bbd789b18ee36842bc971927e5b7fc72a9b0c4dd'),
('jsturleyq', '7deb770267d14dc2d020bcbd22e0ed700dee2f964ed142707ca419ae09ad9765'),
('kbaff14', '55ab6ec242899279e54bb69d640a71c64c5d69bf71fdeb08acad3923e210ae18'),
('kronen', '8da2021bccda5f87b7c4121aca4622f4545e9086d57a185b635eeafbf683cfa9'),
('ksimkovichb', 'fc4669480ae7bdb74465969a371a23fd65037d712b7cdbe171f69b7b136bd04a'),
('lgayton18', '77c59c295926936b037c5ad448d73b51fa0fd60463c6103995c096fdc74f7dc3'),
('lgoldby11', 'e1735ce37f3275cac37b006a0453da3120fb69801441754fa56d23e7d6826128'),
('lvosseg', '41bce96480032a2cef4e9da4fba5549f9c66c41257bcc331f5d3d39d75663e93'),
('mcracknallz', '779a8ef8733f4e5c95da320465ac9e0fe75f8d337668808a45942ed90e378be6'),
('mmorefieldv', '92d9cb18559467228757be1ee4ac48eb66bc1e195df371a9b90b8721fd04698a'),
('mpountney2', '6fed611a9951ace3623e8b23f255007ff37c37135a40e45a6d3e6423d3c88f84'),
('mpoxon10', '75189da0eeaabc09c46459f31e1ce0671d535595f9c21b8f9c687d04f54f6263'),
('myepiskopov0', '1ba9f73fdfc3599fe22a5eb1aa75cc829b13a5ff128dd02268bd6d63aa3f78e2'),
('nbernardot16', 'e67cd313d9e9abfdccf154734334d880f4134bafed1446b5553b496e1fca6fa1'),
('ncornilll', '0ecf71597e80c1c57ba74c40b793156ae2ad9ce613b94403a1c091c136077a1b'),
('nsabey13', '735ad19ba50f87d92d4827839fe7b64fa6bf7539bfa22342bef87b5f5c57dad5'),
('pblewmenc', '9ba5196c29c2d119da217c05a73b98c789ff246325311073e76318a92101eae3'),
('pcovills', '1b870de6ce4acd02c0ce95f2ec3c7d1abf2287183501bbcea05e6b125b56e7ae'),
('pvarnsf', '181aad2a3040535070b5062a53f0ca403a8799614b6e870ffcf1873f90a016ff'),
('pwoodruffed', '468e6f8c410700d95c90395bd69960996dd43fd61dc90bb28636632726f0c419'),
('qmcgeaney1c', '74a1a00d92eae582936237cc32b20218fd4dd50ddd09f29d3f9b670bfe7ffba5'),
('rmurkus5', '004b9e5cfd763903b9dc551107f1d93faa31fcecd4e386253b5110be3b94cb84'),
('rvaillanty', 'd38026c6b97f3eb08da5d40926a07911661ab7c048a3e708c00bcbb231fda47f'),
('sciciari3', '4eff9dd4fd65fccf5fb70be30202cf18ee9d67adb13772becea5d51f6fb1bf6a'),
('scirlosj', 'b6a5d1c50eeb4b04a233d45e8672c2255bc1177e5b617312f33728be93abd097'),
('scodringtone', '17e644cb6fd900c774c2ed72df4741c406f7fa3e50f9596baa48c25567915758'),
('sdu1a', 'a31ff09b44525724273bfa48f9b4ace4d2fb7efde6483618c73a391f07eba8f1'),
('tbloorer', '557c1df5f69ad5f15bf6db3503fac705f97b5e6c5943136c16b168a2e7cb1bb9'),
('test', '9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08'),
('test3', 'fd61a03af4f77d870fc21e05e7e80678095c92d808cfb3b5c279ee04c74aca13'),
('tmanfordx', '6b6d86e1b27626a35943871e0e19c80de8f25205fba24f73b1f79e8d820a7c76'),
('vjedrysik6', 'c22c95bf0f53c57f8b4418e958ac9d0b9a01ade9da4f661281887994177ea0e5'),
('wlipyeati', '46aad3d432c278b0a733696199c780d30bd2a4e60494659c3971f36bf60593d6'),
('wtopley12', '4e5670fc2e6e2edb81c62c468dd352e85affe5079ead40c0ef149c8fa37c6fa5');

INSERT INTO `user_role` (`user_name`, `role_name`) VALUES
('admin', 'admin'),
('test', 'autorizado');


DROP TABLE IF EXISTS `tag`;
CREATE TABLE `tag` (
  `id` int(11) NOT NULL,
  `tag_name` varchar(81) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `tag` (`id`, `tag_name`) VALUES
(4, '#ChirperRules');

DROP VIEW IF EXISTS `post_followees`;
CREATE ALGORITHM=UNDEFINED DEFINER=`proyecto`@`localhost` SQL SECURITY DEFINER VIEW `post_followees`  AS  (select uuid() AS `id`,`p1`.`id` AS `id_follower`,`p1`.`user` AS `follower_name`,`p2`.`user` AS `followee_name`,`p2`.`full_name` AS `followee_full_name`,`p`.`id` AS `post`,`p`.`text` AS `text`,`p`.`original_post` AS `original_post`,`p`.`pub_date` AS `pub_date`,`p`.`latitude` AS `latitude`,`p`.`longitude` AS `longitude`,`p`.`likes` AS `likes` from (((`profile` `p1` join `follower_followee` `f` on((`p1`.`id` = `f`.`follower`))) join `profile` `p2` on((`p2`.`id` = `f`.`followee`))) join `post` `p` on((`p`.`author` = `p2`.`id`)))) ;

DROP VIEW IF EXISTS `trending_topics`;
CREATE ALGORITHM=UNDEFINED DEFINER=`proyecto`@`localhost` SQL SECURITY DEFINER VIEW `trending_topics`  AS  select uuid() AS `id`,`t`.`id` AS `id_tag`,`t`.`tag_name` AS `tag_name`,count(`pt`.`tag`) AS `post_count` from (`post_tag` `pt` left join `tag` `t` on((`pt`.`tag` = `t`.`id`))) group by `pt`.`tag` ;


ALTER TABLE `follower_followee`
  ADD PRIMARY KEY (`follower`,`followee`),
  ADD KEY `followee` (`followee`);

ALTER TABLE `post`
  ADD PRIMARY KEY (`id`),
  ADD KEY `author` (`author`);

ALTER TABLE `post_tag`
  ADD PRIMARY KEY (`post`,`tag`),
  ADD KEY `tag` (`tag`);

ALTER TABLE `private`
  ADD PRIMARY KEY (`id`),
  ADD KEY `sender` (`sender`),
  ADD KEY `receiver` (`receiver`);

ALTER TABLE `profile`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user` (`user`);

ALTER TABLE `role`
  ADD PRIMARY KEY (`role_name`);

ALTER TABLE `tag`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `tag_name` (`tag_name`);

ALTER TABLE `user`
  ADD PRIMARY KEY (`user_name`);

ALTER TABLE `user_role`
  ADD PRIMARY KEY (`user_name`,`role_name`);


ALTER TABLE `post`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;
ALTER TABLE `private`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
ALTER TABLE `profile`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=103;
ALTER TABLE `tag`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

ALTER TABLE `follower_followee`
  ADD CONSTRAINT `followee_ibfk_2` FOREIGN KEY (`followee`) REFERENCES `profile` (`id`),
  ADD CONSTRAINT `follower_ibfk_1` FOREIGN KEY (`follower`) REFERENCES `profile` (`id`);

ALTER TABLE `post`
  ADD CONSTRAINT `post_ibfk_1` FOREIGN KEY (`author`) REFERENCES `profile` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `post_tag`
  ADD CONSTRAINT `post_ibfk_2` FOREIGN KEY (`post`) REFERENCES `post` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `tag_ibfk_1` FOREIGN KEY (`tag`) REFERENCES `tag` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `private`
  ADD CONSTRAINT `private_ibfk_1` FOREIGN KEY (`sender`) REFERENCES `profile` (`id`),
  ADD CONSTRAINT `private_ibfk_2` FOREIGN KEY (`receiver`) REFERENCES `profile` (`id`);

ALTER TABLE `profile`
  ADD CONSTRAINT `profile_ibfk_1` FOREIGN KEY (`user`) REFERENCES `user` (`user_name`) ON DELETE CASCADE ON UPDATE CASCADE;
SET FOREIGN_KEY_CHECKS=1;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
