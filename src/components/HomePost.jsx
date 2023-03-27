import React from 'react';
import {NavLink} from 'react-router-dom';


export default function HomePost({post}) {
	return (
		<div className="HomePost">
			<NavLink className="PostContainer"
			 		to={'/post'} 
					state={{pid: post._id}}>
				
				<h1 className="PostInfo">{post.title} <br/>
					<div className="PostPrice">${post.price}</div></h1>
			</NavLink> 
			
			<NavLink 
				to={'\profile'}
				state={{sellerID: post.seller._id}}>
				<h4 className="PostSeller">{post.seller.name}</h4>
			</NavLink>
		</div>
	)
}
