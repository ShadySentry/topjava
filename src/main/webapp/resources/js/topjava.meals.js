const mealsAjaxUrl = "ajax/profile/meals/";

function updateFilteredTable() {
    $.ajax({
        type: "GET",
        url: mealsAjaxUrl+"filter",
        data: $("#filter").serialize()
    }).done(updateTableByData);
}
// date/time picker
$('#datetimepicker').datetimepicker({
    format:'d.m.Y H:i'
});

$('#image_button').click(function(){
    jQuery('#datetimepicker').datetimepicker('show'); //support hide,show and destroy command
});

function clearFilter() {
    $("#filter")[0].reset();
    $.get("ajax/profile/meals/", updateTableByData);
}

$.ajaxSetup({
    converters: {
        "text json": function (stringData) {
            const json = JSON.parse(stringData);
            $(json).each(function () {
                this.dateTime = this.dateTime.replace('T', ' ').substr(0, 16);
            });
            return json;
        }
    }
});

$(function () {
    makeEditable({
        ajaxUrl: mealsAjaxUrl,
        datatableApi: $("#datatable").DataTable({
            "ajax": {
                "url": mealsAjaxUrl,
                "dataSrc": ""
            },
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime",
                    // "render": function (data, type, row) {
                    //     if(type=="display"){
                    //         return data.replace("T"," ")
                    //     }
                    //     return data;
                    // }
                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
                },
                {
                    "orderable": false,
                    "defaultContent": "",
                    "render": renderEditBtn
                },
                {
                    "orderable": false,
                    "defaultContent": "",
                    "render": renderDeleteBtn
                },

            ],
            "order": [
                [
                    0,
                    "desc"
                ]
            ],
            "createdRow":function (row, data, dataIndex) {
                $(row).attr("data-mealExcess",data.excess)
            }
        }),
        updateTable: updateFilteredTable
    });
});