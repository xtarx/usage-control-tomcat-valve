function addStyleString(str) {
    var node = document.createElement('style');
    node.innerHTML = str;
    document.body.appendChild(node);
}

function inject_circle() {
    addStyleString('.circle {  position:absolute; }');
    addStyleString('.circle { bottom: 4px; }');
    addStyleString('.circle { right: 4px; }');
    addStyleString('.circle { width: 90px;}');
    addStyleString('.circle { height: 90px;}');
    addStyleString('.circle { border-radius: 10%;}');
    addStyleString('.circle { font-size: 10px;}');
    addStyleString('.circle { color: #fff;}');
    addStyleString('.circle { background: #f1c40f}');
    var html = '<div class="circle">Deploy a policy ';
    html += '<ul type="circle">'
    html += ' <li onclick="selectPolicy(this,1)">passwords</li>'
    html += '<li onclick="selectPolicy(this,2)">emails</li>'
    html += '<li onclick="selectPolicy(this,3)">Beetroot</li>'
    html += '</ul>'
    html += '</div>'
    document.body.innerHTML = document.body.innerHTML + html;
}

function selectFields(policy_id) {
    sels = document.getElementsByTagName('input');
    for (i = 0; i < sels.length; i++) {
        sels[i].addEventListener("click", function(event) {
            var targetElement = event.target || event.srcElement;
            console.log(targetElement);
            console.log(targetElement.type);
            targetElement.setAttribute("concon", "sssc_policy_" + policy_id + "_" + targetElement.name);
            targetElement.style["background-color"] = "#f1c40f"
        }, false);
    }
}
current_policy = 0;

function selectPolicy(el, id) {
    if (current_policy == id) {
        current_policy = 0;
        el.style["background-color"] = ""
        return;
    }
    current_policy = id;
    el.style["background-color"] = "#fff"
    selectFields(current_policy);
}
inject_circle()
    // var canvas = document.getElementById('myCanvas');
    // var context = canvas.getContext('2d');
    // var centerX = canvas.width / 2;
    // var centerY = canvas.height / 2;
    // var radius = 20;
    // context.beginPath();
    // context.arc(centerX, centerY, radius, 0, 2 * Math.PI, false);
    // context.fillStyle = '#f1c40f';
    // context.fill();
    // context.lineWidth = 1;
    // context.strokeStyle = '#ecf0f1';
    // context.stroke();