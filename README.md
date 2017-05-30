# Chirper

Chirper is a microblogging platform developed with Java EE
Chirper is meant to replicate the basic feature set of a microblogging platform.
That means: finding users, browsing profiles, follow/unfollow, send text posts limited to 256 characters, 
retransmiting and direct/private messages, search of words in all posts, trending topics... 

## Getting Started

### Prerequisites

* Apache Tomcat is used for development. 
* MySQL/MariaDb database and connector.

### Installing

    * Add mysql-connector to Tomcat's lib folder.
    * Database scripts are supplied, you can find them in the database folder
    * Configure tomcat-users.xml with your settings. E.g.:
        <user password="your_pass" roles="admin-gui,manager-gui,manager-script,admin" username="your_username"/>
    * Set environment variables EMAIL_NOTIFIER_HOST, EMAIL_NOTIFIER_PORT, EMAIL_NOTIFIER_USER
    and EMAIL_NOTIFIER_PASS to your smtp service (SSL) for email notifications. 
    E.g.:
        EMAIL_NOTIFIER_HOST=smtp.gmail.com
        EMAIL_NOTIFIER_PORT=465
        EMAIL_NOTIFIER_USER=your_gmail_user 
        EMAIL_NOTIFIER_PASS=your_gmail_pass
    * Change META-INF/context.xml to your database settings.
    * A build project for Netbeans is supplied, so open it with Netbeans and build.

## Built With

* [JAVA EE](http://www.oracle.com/technetwork/java/javaee/overview/index.html)
* [JSF](http://www.oracle.com/technetwork/java/javaee/javaserverfaces-139869.html)
* [Java Persistence API](http://docs.oracle.com/javaee/6/tutorial/doc/bnbpz.html)
* [Primefaces](https://www.primefaces.org/) - Open source UI framework for JSF
* [PrettyFaces](http://www.ocpsoft.org/prettyfaces/) - URL-rewriting library with enhanced support for JavaServer Faces, used to create  bookmark-able, pretty URLs for the users profiles
* [Apache Commons Codec](https://commons.apache.org/codec/) - provides implementations of common encoders and decoders, used to encode and decode passwords (SHA256hex)
* [Apache Commons IO](https://commons.apache.org/proper/commons-io/) - utilities to assist with developing IO functionality
* [Easy Responsive Tabs](https://webthemez.com/demo/easy-responsive-tabs/Index.html) - is a lightweight jQuery plugin which optimizes normal horizontal or vertical tabs to accordion.
* [jQuery scrollex](https://github.com/ajlkn/jquery.scrollex) - Scroll events for jQuery.
* [jQuery scrolly](https://github.com/Victa/scrolly) - Parallax scroll.
* [Notify.js] https://notifyjs.com/ - jQuery plugin to provide simple yet fully customisable notifications.

## Versioning

I use [SemVer](http://semver.org/) for versioning. For the versions available, see the [tags on this repository](https://github.com/Kronen/chirper/tags). 

## Authors

* **Alberto G. Lagos** -  [Kronen](https://github.io/Kronen)


