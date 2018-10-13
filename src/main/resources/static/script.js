$(document).ready(function(){
    $("#btnlogin").click(function(){
        $("#mdlogin").modal();
    });
    $("#btndangky").click(function(){
        $("#mddangky").modal();
    });
});
function TrangThaiHang(){
	
    
	 var n= document.getElementById("trangthai").textContent;

if(n>0)
	{
	
	document.getElementById("trangthai").innerHTML = "Còn hàng";
	} 
else
document.getElementById("trangthai").innerHTML ="Hết Hàng";

	

	var i;
	 var dg= document.getElementById("danhgia").textContent;
	 document.getElementById("danhgia").innerHTML = '<img src="../image/rated.png" />'; 
for( i=4; i<=dg; i+=2)
	{
	document.getElementById("danhgia").innerHTML += '<img src="../image/rated.png" />'; 
	
	}
if(dg%2!=0){
	document.getElementById("danhgia").innerHTML += '<img src="../image/nua_rate.png" />'; 
	i++;
}
for(var j=i; j<=10;j+=2){
	document.getElementById("danhgia").innerHTML += '<img src="../image/none_rate.png" />'; 
}
document.getElementById("insoluong").innerHTML = "1";
};

function SoSao(){
	var i;
	 var dg= document.getElementById("danhgia").textContent;
	 document.getElementById("danhgia").innerHTML = '<img src="../image/rated.png" />'; 
for( i=4; i<=dg; i+=2)
	{
	document.getElementById("danhgia").innerHTML += '<img src="../image/rated.png" />'; 
	
	}
if(dg%2!=0){
	document.getElementById("danhgia").innerHTML += '<img src="../image/nua_rate.png" />'; 
	i++;
}
for(var j=i; j<=10;j+=2){
	document.getElementById("danhgia").innerHTML += '<img src="../image/none_rate.png" />'; 
}


};
	

	