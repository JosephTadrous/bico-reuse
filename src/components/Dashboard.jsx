import React from 'react';
import {useEffect, useState } from 'react'; 
import Plot from 'react-plotly.js';

function formatDate(date){

    var dd = date.getDate();
    var mm = date.getMonth()+1;
    if(dd<10) {dd='0'+dd}
    if(mm<10) {mm='0'+mm}
    date = mm+'/'+dd
    return date
 }

function Last7Days () {
    var result = [];
    for (var i=0; i<7; i++) {
        var d = new Date();
        d.setDate(d.getDate() - i);
        result.push(d)
    }
    result.reverse()
    return result;
}

export default function Dashboard() { 
    const [data, setData] = useState([]);
    //const [isBusy, setBusy] = useState(true)

	useEffect( () => {
		fetch('http://localhost:3000/dashboard')
		.then((res) => res.json())
		.then((data) => {
			setData(data);
		});
	});

    //while (setBusy) { 
    //    console.log("waiting for data")
    // }
	return <html className="EditForm"> 
    <div> Number of available posts: {data.post_count} </div>
    <div> Number of users: {data.user_count} </div>
    <Plot
        data={[
          {type: 'scatter', x: Last7Days(), y: [2, 5, 3, 4, 5, 2, 9]},
        ]}
        layout={ {width: 640, height: 480, title: 'Number of posts per day (last 7 days)'} }
      />
    </html>
}