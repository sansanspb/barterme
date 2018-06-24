'use strict';

module.exports = function () {


    var service = {
        mapTree: mapTree
    };

    function mapTree(inputArr) {
        var resultTree = [],
            mappedArr = {};

        for (var i = 0; i < inputArr.length; i++) {
            mappedArr[inputArr[i].categoryId] = inputArr[i];
            mappedArr[inputArr[i].categoryId]['childs'] = [];
        }

        for (var categoryId in mappedArr) {
            if (mappedArr[categoryId].parentId != null) {
                mappedArr[mappedArr[categoryId].parentId]['childs'].push(mappedArr[categoryId]);
            } else {
                resultTree.push(mappedArr[categoryId]);
            }
        }

        return resultTree;
    }

    return service;
};