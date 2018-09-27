$(function() {
    $.poa.code = {
        init: function() {
            // get all code type and set to code type select..
            self._getAllCodeType();
            // init code table.
            self._initCodeTable();
            // init button event.
            self._initButtonEvt();
        },
        _getAllCodeType: function() {
            $.poa.ajax({
                url: 'code/getAllTypes',
                type: 'get',
                dataType: 'json',
                success: self._setCodeTypeSelect
            });
        },
        _setCodeTypeSelect: function(data) {
            var codeTypeSelect = $('#codeType');
            codeTypeSelect.empty();
            $('<option></option>').appendTo(codeTypeSelect);
            $.each(data, function(indx, item) {
                var opt = $('<option></option>');
                opt.val(item.codeTypeId);
                opt.text(item.codeTypeName);
                opt.appendTo(codeTypeSelect);
            });
        },
        _initCodeTable: function() {
            $.poa.table.create({
                selector: '#codeTable',
                url: 'code/codeList',
                sortName: 'CODE_TYPE_ID',
                pageSize: 5,
                pageList: [5, 10, 20, 50],
                columns: [{
                    checkbox: true
                }, {
                    field: 'codeTypeName',
                    title: '模块类型'
                }, {
                    field: 'codeName',
                    title: '模块名'
                }],
                queryParams: function(params) {
                    var codeTypeId = $('#codeType').val();
                    var codeNameLike = $('#codeNameLike').val();
                    return {
                        limit: params.limit,
                        offset:params.offset,
                        sortOrder: params.order,
                        sortField: params.sort,
                        codeTypeId: codeTypeId,
                        codeNameLike: codeNameLike
                    };
                }
            });
        },
        _initButtonEvt: function() {
            $('#codeSearch').click(function() {
                $.poa.table.refresh({
                    selector: '#codeTable',
                    params: {
                        silent: true
                    }
                });
            });
            $('#addCode').click(function() {
                // TODO:
                alert('TODO');
            });
            $('#deleteCode').click(function() {
                var selections = $.poa.table.getSelections('#codeTable');
                var codeIdList = [];
                $.each(selections, function(indx, item) {
                    codeIdList.push(item.codeId);
                });
                if (codeIdList.length === 0) {
                    $.poa.messageBox.alert($.poa.resource.CODE_NO_SELETION);
                    return;
                }
                $.poa.messageBox.confirm($.poa.resource.CODE_DELETE_CONFIRM + codeIdList.length, '', {
                    yes: function() {
                        $.poa.ajax({
                            url: 'code/delete',
                            type: 'post',
                            dataType: 'json',
                            contentType: 'application/json',
                            data: JSON.stringify(codeIdList),
                            success: function(data) {
                                $.poa.table.refresh({
                                    selector: '#codeTable',
                                    params: {
                                        silent: true
                                    }
                                });
                                $.poa.messageBox.info($.poa.resource.CODE_DELETE_SUCCESS);
                            }
                        });
                    }
                });
            });
        }
    };
    var self = $.poa.code;
});