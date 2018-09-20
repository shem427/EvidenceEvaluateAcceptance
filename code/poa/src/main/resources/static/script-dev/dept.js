$(function() {
    $.poa.dept = {
        init: function() {
            // init Tree
            self._initTree();
        },
        _initTree: function() {
            $.poa.tree.create({
                selector: '#deptTree',
                url: 'dept/deptTree',
                checkEnabled: false,
                editEnabled: false,
                selectedMulti: false,
                callback: {

                }
            });
        }
    };
    var self = $.poa.dept;
});