var express    = require('express');
var app        = express();
var sillytasty = require('./lib/sillytasty');

/* body parser is deprecated
var bodyParser = require('body-parser');

// this lets us get data from POST
app.use(bodyParser());
*/

var port = process.env.PORT || 8080;

var router = express.Router();

router.get('/', function(req, res) {
    res.json({ message: 'welcome to the shelf life api' });
});

router.get('/search', function(req, res) {
    var query = req.query.q;
    sillytasty.doSearch(query, function(error, results) {
        if (error) {
            return res.json({ error: error.errno });
        }
        return res.json({
            q: query,
            results: results
        });
    });
});

router.get('/shelflife/:id', function(req, res) {
    var itemId = parseInt(req.params.id);
    if (isNaN(itemId)) {
        return res.json({
            error: 400,
            msg: 'Bad request'
        });
    }

    sillytasty.getShelfLife(itemId, function(error, results) {
        if (error) {
            return res.json({
                error: error.code,
                message: error.errno
            });
        }
        return res.json({
            id: itemId,
            results: results
        });
    });
});

app.use('/api', router);

app.listen(port);
console.log('app listening on port ' + port);
