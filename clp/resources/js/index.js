function show(query, visible) {
	let elements = document.querySelectorAll(query);
	for (let element of elements) {
		if (visible) {
			element.style.display = "";
		} else {
			element.style.display = "none";
		}
	}
}

function showLamps(visible) {
	show(".lampada", visible);
}

function showLogicStates(visible) {
	show(".estado-logico", visible);
}

function set(query, value) {
	document.querySelector(query).innerHTML = value;
}

function defaultValues() {
	fetch("post/values/default", { method: "POST" });
}

function randomValues() {
	fetch("post/values/random", { method: "POST" });
}

function updateInterface(data) {			
	if (!document.getElementById("I0estado"))
		return;
	
	let coils = data.coils;
	for (let i in coils) {
		let intValue = coils[i] ? 1 : 0;
		
		show(`#Q${i}lamp-on`, coils[i]);
		show(`#Q${i}on`, coils[i]);
		set(`#Q${i}value`, intValue);
	}
	
	let discreteInputs = data.discreteInputs;
	for (let i in discreteInputs) {
		let intValue = discreteInputs[i] ? 1 : 0;
		
		show(`#I${i}on`, discreteInputs[i]);
		set(`#I${i}value`, intValue);
	}
	
	let holdingRegisters = data.holdingRegisters;
	for (let i in holdingRegisters) {
		set(`#HR${i}value`, holdingRegisters[i]);
	}
	
	let inputRegisters = data.inputRegisters;
	set(`#IR0value`, inputRegisters[0]);
	set(`#IR1value`, inputRegisters[1].toFixed(2));
	
}

function update() {
	fetch("get/values").then(response => {
		response.json().then(json => updateInterface(json));
	});
}

fetch("resources/images/PLC Protut.svg").then(response => {
	if (response.ok) {
		response.text().then(text => {

			document.getElementById("svg-container").innerHTML = text;
			
			for (let i of document.querySelectorAll("input[type='checkbox']")) {
				i.checked = false;
			}
			
			showLamps(false);
			showLogicStates(false);
		});
	}
});

fetch("get/config").then(response => {
	if (response.ok) {
		response.json().then(json => {
			let slaveIdSpan = document.getElementById("slave-id");
			let tcpPortSpan = document.getElementById("tcp-port");
			let keepAliveSpan = document.getElementById("use-keep-alive");
			
			slaveIdSpan.innerText = json["slave_id"];
			tcpPortSpan.innerText = json["tcp_port"];
			keepAliveSpan.innerText = json["use_keep_alive"] ? "ON" : "OFF";
		});
	}
});

setInterval(update, 100);
