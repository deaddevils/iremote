function init() {
    $(document).ready(function(){
        $("#region").change(function(){
            var id = $(this).children('option:selected').val();
            alert(id);
            initRegion(2, id);
        });
        $("#province").change(function(){
            var id = $(this).children('option:selected').val();
            alert(id);
            initRegion(3, id);
        });
        $("#city").change(function(){
            var id = $(this).children('option:selected').val();
            alert(id);
            initRegion(4, id);
        });
    });
}

function initRegion(type, objid) {
    $.ajax({
        type: "get",
        url: "../data/querylocationdatastep",
        data:{
            type : type,
            objid: objid
        },
        async: true,
        success: function (data) {
            if (data.resultCode != 0) {
                return
            }
            processorAddressData(data, type)
        }
    });
}

function processorAddressData(data, type) {
    if (data.resultCode != 0) {
        return;
    }
    var selectBox = document.getElementById(getAreaNameByType(type));
    alert(data.addrinfo.length)
    for (i in data.addrinfo) {
        selectBox.options.add(new Option(data.addrinfo[i].name, getAreaIdValueByType(data, type)));
    }
}

function getAreaIdValueByType(data, type) {
    switch (type) {
        case 1:
            return data.addrinfo[i].regionid;
        case 2:
            return data.addrinfo[i].provinceid;
        case 3:
            return data.addrinfo[i].cityid;
        case 4:
            return data.addrinfo[i].countyid;
    }
}

function getAreaNameByType(type) {
    switch (type) {
        case 1:
            return "region";
        case 2:
            return "province";
        case 3:
            return "city";
        case 4:
            return "county";
    }
}