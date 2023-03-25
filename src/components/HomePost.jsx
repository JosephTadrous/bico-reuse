import React from 'react';
import {NavLink} from 'react-router-dom';


export default function HomePost({post}) {
	return (
		<div className="HomePost">
			<NavLink className="Container" to= {'/post?pid=' + post._id} >
				<h1>{post.title} - ${post.price}</h1>	
				<p>{post.seller.name}</p>
			</NavLink> 
		</div>
	)
}
