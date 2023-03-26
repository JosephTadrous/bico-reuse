import React from 'react';
import {NavLink} from 'react-router-dom';


export default function HomePost({post}) {
	return (
		<div className="HomePost">
			<NavLink className="Container"
			 		to={'/post'} 
					state={{pid: post._id}}>
				<h1>{post.title} - ${post.price}</h1>
			</NavLink> 
			
			<NavLink 
				to={'/profile'}
				state={{sellerId: post.seller._id}}>
				<h3>{post.seller.name}</h3>
			</NavLink>
		</div>
	)
}
