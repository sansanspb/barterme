'use strict';

var angular = require('angular');
var barterme = angular.module('barterme', []);

// one require statement per sub directory instead of one per file
require('./controller');
require('./service');
require('./filter');
require('./directive');
