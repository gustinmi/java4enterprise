 $(document).ready(function () {
    var arr,states,jqTbl,jqRemoveAll,jqAddNew,dummyIndex = 0,logoutLink;
    
    states = {
        'table1' : {
            'bind' : function(){
                portal.log.info('Starting to bind ui');
                arr = [{'id':'5','name':'john'},{'id':'6','name':'jack'},{'id':'8','name':'johannes'}];
                jqTbl = $('table.grid');
                jqRemoveAll = $('button.removeAll');
                jqAddNew = $('button.addNew');
                jqRemoveAll.on('click', states.table1.rmAll);
                jqAddNew.on('click', states.table1.add.draw);
                states.table1.render(arr);
                
                logoutLink = $('<a>',
                  {
                    text: 'Logout, ' + USER,
                    title: 'Logout',
                    href: CONTEXT_PATH + '/Logout'
                  }
                ).appendTo('div.logout');
                
                $('div.logout').clone().appendTo('div.menu.footer');
                
            },
            'render' : function(myArr){
                jqTbl.find('tbody').empty();
                for(var i=0;i<myArr.length;i++){
                    var data = myArr[i],tr,td,btnRm;
                    tr = $('<tr/>');
                    tr.data('id',data['id']);
                    td = $('<td>' + data['id'] + '</td>');
                    tr.append(td);
                    td = $('<td>' + data['name'] + '</td>');
                    tr.append(td);
                    td = $('<td/>');
                    btnRm = $('<button/>',
                        {
                            text: 'Remove',
                            click: states.table1.rowRm
                        });
                    td.append(btnRm);
                    tr.append(td);
                    jqTbl.find('tbody').append(tr);
                    jqTbl.delegate("tr", "click", function(e) {
                        e.preventDefault();
                        e.stopImmediatePropagation();
                        var id = $(this).data('id');
                    });
                }
            },
            'rowRm':function(el){
                el.preventDefault();
                el.stopImmediatePropagation();
                var el2 = $(this).parent().parent();
                var idFilter = el2.data('id');
                arr = $.grep(arr, function(el, index){
                    return el.id !== idFilter;
                });
                states.table1.render(arr);
            },
            'rmAll' : function(){
                arr = [];
                states.table1.render(arr);
            },
            'add': {
                'draw' : function(){
                    var tfooter,tr,td,btnAdd,input,btnCancel;
                    tfooter = $('<tfoot/>');
                    tr = $('<tr/>');
                    tr.data('id','new');
                    td = $('<td />', {colspan:3,text:'Add new person'});
                    input = $('<input/>',{
                        class : 'newName',
                        name : 'newName',
                        type: 'text'
                    });
                    td.append(input);
                    btnAdd = $('<button/>',
                            {
                                text: 'Confirm',
                                click: states.table1.add.handle
                            });
                    td.append(btnAdd);
                    btnCancel = $('<button/>',
                            {
                                text: 'Cancel',
                                click: states.table1.add.cancel
                            });
                    td.append(btnCancel);
                    tr.append(td);
                    tfooter.append(tr);
                    jqTbl.append(tfooter);
                    input.focus();
                },
                'handle' : function(){
                    var val = $('tfoot input[type="text"].newName').val();
                    arr.push({'id':'new' + dummyIndex, 'name':val});
                    states.table1.render(arr);
                    var row = $(this).parent().parent();
                    row.remove();
                    dummyIndex++;
                },
                'cancel' : function(e){
                    var row = $(this).parent().parent();
                    row.remove();
                },
                'clean' : function(){
                    $('table.branch tfoot').empty();
                }
            },
            "close" : function(){
                jqTbl.remove();
                jqRemoveAll.remove();
                jqAddNew.remove();
                jqRemoveAll.remove();
                jqAddNew.remove();
            }
        }
    };

    states.table1.bind();
});