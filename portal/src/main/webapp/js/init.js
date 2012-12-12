// global namespace object
window.gostinec = {};

//create logging functions
gostinec.log = {};
(function () {
    var assembleOutput = function (stuff, e) {
        var lines = [ stuff ];
        if (e && e.stack) lines.push(e.stack);
        return lines.join('\n');
    };
    //for chrome
    if (window.console && console.log) {
        gostinec.log.debug = function (stuff, e) { console.log(assembleOutput('DEBUG: ' + stuff, e)); };
        gostinec.log.info = function (stuff, e) { console.log(assembleOutput('INFO: ' + stuff, e)); };
        gostinec.log.error = function (stuff, e) { console.log(assembleOutput('ERROR: ' + stuff, e)); };
    }
    else { //for firefox
        gostinec.log.debug = function (stuff, e) {
            dump(assembleOutput('DEBUG: ' + stuff, e));
        };
        gostinec.log.info = function (stuff, e) {
            dump(assembleOutput('INFO: ' + stuff, e));
        };
        gostinec.log.error = function (stuff, e) {
            dump(assembleOutput('ERROR: ' + stuff, e));
        };
    }
})();