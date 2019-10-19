# Shelf Life [![Build Status](https://travis-ci.org/jcomo/shelf-life.svg?branch=master)](https://travis-ci.org/jcomo/shelf-life)

An API to get information about the shelf life of food and tips about how to keep food fresh. The data provided in this API comes from [Still Tasty](http://stilltasty.com).

The service is available via heroku at `https://shelf-life-api.herokuapp.com`.

### Routes

* `/search` searches for food guides using the `q` query string paramter
* `/guides/:guideId` returns a food guide for the specific item
