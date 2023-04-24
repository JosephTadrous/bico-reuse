import React from 'react';
import {useEffect, useState } from 'react'; 
import Plot from 'react-plotly.js';
import './Dashboard.css';

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
    for (var i=0; i<8; i++) {
        var d = new Date();
        d.setDate(d.getDate() - i);
        result.push(d)
    }
    result.reverse()
    return result;
}

export default function Dashboard() { 
    const [data, setData] = useState([]);
    const [week, setWeek] = useState([]);
    const [itemSold, setItemSold] = useState([])
    //const [isBusy, setBusy] = useState(true)

 useEffect( () => {
  fetch('http://localhost:3000/dashboard')
  .then((res) => res.json())
  .then((data) => {
   setData(data);
  });
 });

    useEffect( () => {
  fetch('http://localhost:3000/last_week')
  .then((res) => res.json())
  .then((week) => {
   setWeek(week);
  });
 });

    useEffect( () => {
  fetch('http://localhost:3000/items_sold')
  .then((res) => res.json())
  .then((itemSold) => {
   setItemSold(itemSold);
  });
 });

    //while (setBusy) { 
    //    console.log("waiting for data")
    // }
 return <div className="EditForm"> 
    <div> Number of available posts: {data.post_count} </div>
    <div> Number of users: {data.user_count} </div>
    <div> Number of items sold: {itemSold }</div>
    <Plot
        data={[
          {type: 'bar', x: Last7Days(), y: week},
        ]}
        layout={ {width: 640, height: 480, title: 'Number of posts per day (last 7 days)'} }
      />
    </div>
}