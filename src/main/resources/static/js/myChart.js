var niceChartData = decodeHtml(chartData);
var chartJson = JSON.parse(niceChartData);
var numericData =[];
var labelData = [];

for(var i = 0; i < chartJson.length ; i++) {
	numericData[i] = chartJson[i].value;
	labelData[i] = chartJson[i].label;
}										   
const data = {
	labels: labelData,
	datasets: [{
		  label: 'DataSet',
		  backgroundColor: ['#003f5c', '#bc5090', '#ffa600'],
		  data: numericData
	}]
};

const config = {
	type: 'pie',
	data: data,
	options: {
		plugins: {
			title: {
				display: true,
				text: 'Project statuses'
			}
		}
	}
};

const myChart = new Chart(
	document.getElementById('myPieChart'),
	config
);
function decodeHtml(html) {
	var txt = document.createElement("textarea");
	txt.innerHTML = html
	return txt.value;
}