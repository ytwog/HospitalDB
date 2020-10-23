const _DEBUGGING = false;
const _RACE = "Zerg";

function deliver(event) {
    let package = $(`<div>${event.data.paramData}</div>`)
    $('#task-space').append(package);
}

function insertElementsFromJSON(node, placeholderID1, placeholderID2) {
    let itemCount = node.list.length;
    let buildingDiv = $('<div class="row">');
    let unitsDiv = $('<div class="row">');
    $(placeholderID1).append(unitsDiv);
    $(placeholderID2).append(buildingDiv);
    node.list.forEach((element, index) => {
        //let sizeLg = index % 
        let mainWrapper = $('<div class="col-12 p-1 p-md-2 col-md-6 col-xl-4 text-light bg-secondary"></div>');
        let mainDiv = $('<div class="item-wrap bg-dark h-100"></div>');
        let elementImgWrapper = $('<div class="img-wrapper mb-md-2"></div>');
        let elementImg = $(`<img src="WebSecureJS/${element.thumbnail}" alt="${element.alt}" class="item-boxover">`)
        let elementName = $(`<div class="d-none d-sm-block d-lg-block py-1 bg-black d-xl-block"><p class=" p-0 m-0">${element.name}</p></div>`);
        let elementCost = $(`<div class="d-none d-sm-block py-1 pl-1 bg-black d-md-block d-lg-block d-xl-block">
                                <div class="inlined">
                                    <img src="WebSecureJS/images/gGlass.png" alt="Minerals" class="inlined img-sm"> 
                                    <div class="inlined item-info">X${element.costX}</div>
                                </div>
                                <div class="inlined">
                                    <img src="WebSecureJS/images/gGas.png" alt="Minerals" class="inlined img-sm"> 
                                    <div class="inlined item-info">X${element.costY}</div>
                                </div>
                            </div>`);
        elementImgWrapper.append(elementImg);
        mainWrapper.append(mainDiv);
        mainDiv.append(elementImgWrapper);
        mainDiv.append(elementName);
        mainDiv.append(elementCost);
        if(element.isOptional) {
            buildingDiv.append(mainWrapper);
        } else {
            unitsDiv.append(mainWrapper);
        }
        mainDiv.click({paramData: element.name}, deliver);
    });
}

function updateShop() {
    $.getJSON( "WebSecureJS/json/tableData.json", function( data ) {
        if(_DEBUGGING) {
          console.log(data);
        }
        data.list.forEach((element)=>{
            insertElementsFromJSON(element, '#menu-buy-units', '#menu-buy-buildings');
        })
    });
}
updateShop();