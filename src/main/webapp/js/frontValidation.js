
$(function() {

	$("#formAddComputer").submit(function(e){
		
		let computerName= $("#computerName");
		let introduced= new Date($("#introduced").val());
		let discontinued= new Date($("#discontinued").val());
		let noError=true;
		
		console.log("jledéteste");
		if(introduced >0 && discontinued>0){
			console.log("jmedéteste");
			if(introduced>discontinued){
				noError=false;
				console.log("jtedéteste");
				$("#discontinued").after("<span class=\"error\">Discontinued must be after introduced</span>");
			}
		}
		
		if(computerName=="" || computerName == null){
			noError=false;
			$("#computerName").after("Name is mandatory");
		}
		
		if(!noError){
			e.preventDefault();
		}
		
	}); 
});