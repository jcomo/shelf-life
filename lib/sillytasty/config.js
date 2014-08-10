var CONFIG = {
    urls: {
        search: 'http://stilltasty.com/searchitems/search/',
        results: 'http://stilltasty.com/fooditems/index/'
    },
    searchPaths: {
        // the search paths to use when scraping the site
        search: {
            results: '.categorySearch a'
        },
        results: {
            storage: '.slicedHead',
            expiration: '.days'
        }
    }
};

exports.config = CONFIG;
