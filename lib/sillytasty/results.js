var cheerio = require('cheerio');
var request = require('request');
var _       = require('underscore');

var config  = require('./config').config;

var baseUrl = config.urls.results || '';

function makeResultsObj(storageMethods, expirationTimes) {
    var zipped = _.zip(storageMethods, expirationTimes);
    return _.reduce(zipped, function(acc, pair) {
        var storageMethod  = pair[0];
        var expirationTime = pair[1];
        acc.push({
            storage: storageMethod,
            expiration: expirationTime
        });
        return acc;
    }, []);
}

function _parseTextFromSearchPath(dom, searchPath) {
    var results = dom(searchPath);
    return _.map(results, function(el, idx) {
        return dom(el).text();
    });
}

function parseExpirationTimes(dom) {
    var searchPath = config.searchPaths.results.expiration || '';
    return _parseTextFromSearchPath(dom, searchPath);
}

function parseStorageMethods(dom) {
    var searchPath = config.searchPaths.results.storage || '';
    return _parseTextFromSearchPath(dom, searchPath);
}

function parse(html) {
    var $ = cheerio.load(html);
    var storageMethods = parseStorageMethods($);
    var expirationTimes = parseExpirationTimes($);
    return makeResultsObj(storageMethods, expirationTimes);
}

/*
 * callback takes the following parameters:
 *  error - error returned from request
 *  results - an object with the following schema
 *      [
 *          {
 *              storage: <storage_method>,
 *              expiration: <expiration_time>
 *          },
 *          ...
 *      ]
 */
function getShelfLife(itemId, cb) {
    var url = baseUrl + itemId;
    request.get(url, function(error, response, body) {
        if (error) {
            cb(error, null);
        } else {
            var resultsObj = parse(body);
            cb(null, resultsObj);
        }
    });
}

exports.getShelfLife = getShelfLife;
