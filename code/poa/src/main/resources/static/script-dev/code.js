$(function() {
    var self;
    $.poa.code = {
        init: function() {
            // get all code type and set to code type select..
            self._getAllCodeType();
            // init code table.
            self._initCodeTable();
            // init button event.
            self._initButtonEvt();
        },
        initModal: function() {
            self._initModalCodeType();
            self._initModalEvt();
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
                        offset: params.offset,
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
                        silent: true,
                        query: {
                            offset: 0
                        }
                    }
                });
            });
            $('#addCode').click(function() {
                $.poa.modal.create({
                    url: 'code/addPage'
                });
            });
            $('#editCode').click(function() {
                var selections = $.poa.table.getSelections('#codeTable');
                var selected;
                if (selections.length === 0) {
                    $.poa.messageBox.alert($.poa.resource.CODE_NO_SELETION);
                    return;
                } else if (selections.length !== 1) {
                    $.poa.messageBox.alert($.poa.resource.CODE_EDIT_MULTI_SELECT);
                    return;
                }
                selected = selections[0];
                $.poa.modal.create({
                    url: 'code/editPage',
                    data: {
                        codeId: selected.codeId,
                        codeTypeId: selected.codeTypeId,
                        codeName: selected.codeName
                    }
                });
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
                            success: function() {
                                $('#codeSearch').trigger('click');
                                $.poa.messageBox.info($.poa.resource.CODE_DELETE_SUCCESS);
                            }
                        });
                    }
                });
            });
        },
        _initModalCodeType: function() {
            var codeId = $('#selectedCodeId').val();
            var codeTypeSelect = $('#selectedCodeTypeId');
            var otherTypeArea = $('#otherType');
            if (codeId) {
                codeTypeSelect.prop('disabled', true);
            }
            otherTypeArea.hide();
            codeTypeSelect.change(function() {
                if (codeTypeSelect.val() === '-1') {
                    otherTypeArea.show();
                } else {
                    otherTypeArea.hide();
                }
            });
        },
        _initModalEvt: function() {
            $('#saveCode').click(function() {
                var codeTypeSelect = $('#selectedCodeTypeId');
                var otherTypeName = $('#otherTypeName');
                var typeId = codeTypeSelect.val();
                var typeName;
                var codeId = $('#selectedCodeId').val();
                var codeName = $('#selectedCodeName').val();
                if (typeId === '-1') {
                    typeName = otherTypeName.val();
                } else {
                    typeName = codeTypeSelect.find('option:selected').text();
                }
                if (!codeId) {
                    codeId = -1;
                }
                $.poa.ajax({
                    url: 'code/save',
                    type: 'post',
                    dataType: 'json',
                    data: {
                        codeTypeId: typeId,
                        codeTypeName: typeName,
                        codeId: codeId,
                        codeName: codeName
                    },
                    success: function() {
                        $.poa.modal.destroy({
                            selector: '#codeModal'
                        });
                        $('#codeSearch').trigger('click');
                    }
                });
            });
        }
    };
    self = $.poa.code;
});