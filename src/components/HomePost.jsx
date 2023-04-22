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
				to={'/profile'}
				state={{sellerId: post.seller._id}}>
				<h3>{post.seller.name}</h3>
			</NavLink>

			<div>
				{post.approved != true ?
					<form action="http://localhost:3000/approve_post" method="post">
					<input type="hidden" name="id" value= {post._id} />
					<button className="EditButton"> Approve</button>
					</form>: null
				}
			</div>
		</div>
	)
}
