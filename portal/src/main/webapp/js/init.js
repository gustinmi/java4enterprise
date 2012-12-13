// global namespace object
window.portal = {};

//create logging functions
portal.log = {};
(function () {
    var assembleOutput = function (stuff, e) {
        var lines = [ stuff ];
        if (e && e.stack) lines.push(e.stack);
        return lines.join('\n');
    };
    //for chrome
    if (window.console && console.log) {
        portal.log.debug = function (stuff, e) { console.log(assembleOutput('DEBUG: ' + stuff, e)); };
        portal.log.info = function (stuff, e) { console.log(assembleOutput('INFO: ' + stuff, e)); };
        portal.log.error = function (stuff, e) { console.log(assembleOutput('ERROR: ' + stuff, e)); };
    }
    else { //for firefox
        portal.log.debug = function (stuff, e) {
            dump(assembleOutput('DEBUG: ' + stuff, e));
        };
        portal.log.info = function (stuff, e) {
            dump(assembleOutput('INFO: ' + stuff, e));
        };
        portal.log.error = function (stuff, e) {
            dump(assembleOutput('ERROR: ' + stuff, e));
        };
    }
})();