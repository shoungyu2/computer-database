
$(function() {

	$("#formAddComputer").submit(function(e){
		
		let computerName= $("#computerName");
		let introduced= new Date($("#introduced").val());
		let discontinued= new Date($("#discontinued").val());
		let noError=true;
		
		if(introduced >0 && discontinued>0){
			if(introduced>discontinued){
				noError=false;
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
	
	$("#formEditComputer").submit(function(e){
		
		let computerName= $("#computerName");
		let introduced= new Date($("#introduced").val());
		let discontinued= new Date($("#discontinued").val());
		let noError=true;
		
		if(introduced >0 && discontinued>0){
			if(introduced>discontinued){
				noError=false;
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