# spring-data-couchbase-example

Spring Data Couchbase example composed of several steps introducing every features available. The goal is to get some
data out of twitter and put them in Couchbase.

## Step 0

This should give you a ready to start project with the right dependencies so you won't be bother by this aspect. You
start with Spring Boot, Spring Data Couchbase and Spring Social Twitter.

## Step 1 Configuration

Configuration ready + CommandLine Runner to test Bean Injection, and the couchbase configuration

At this point everything is explained in [http://blog.couchbase.com/intro-spring-data-couchbase}(http://blog.couchbase.com/intro-spring-data-couchbase)

## Step 2 Domain Object

Add a TwitterUpdate object as first ODM example, use CouchbaseTemplate instead of COuchbase Client

## Step 3 CRUD Repository

Add the repository, showcase the bulk methods, add the appropriate twitterupdate/all view in Couchbase

## Step 4 Creating a service

Add a service to manage twitter update, test it and introduce custom query and stale option

## Step 5 Query for time series

play with Repository

## Step 6 Expose the repository

Expose the repository with the rest annotation
