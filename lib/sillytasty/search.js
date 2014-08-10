var cheerio = require('cheerio');
var request = require('request');
var _       = require('underscore');

var config = require('./config').config;

var baseUrl = config.urls.search || '';

function parseId(resultUrl) {
    var tokens = resultUrl.split('/');
    var maybeId = tokens[tokens.length - 1];
    if (isNaN(maybeId)) {
        maybeId = null;
    }
    return maybeId;
}

function parse(html) {
    var $ = cheerio.load(html);
    var resultsSearchPath = config.searchPaths.search.results || '';
    var results = $(resultsSearchPath);
    return _.map(results, function(el, idx) {
        var domEl = $(el);
        var url = domEl.attr('href');
        return {
            label : domEl.text(),
            url   : url,
            id    : parseId(url)
        };
    });
};

/*
 * cb is a function with parameters:
 *  error - any error returned
 *  results - an array of results
 */
function doSearch(searchTerm, cb) {
    var url = baseUrl + searchTerm;
    request(url, function(error, response, body) {
        if (error) {
            cb(error, null);
        } else {
            var results = parse(body);
            cb(null, results);
        }
    });
};

exports.doSearch = doSearch;
