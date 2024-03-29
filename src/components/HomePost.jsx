import React from 'react';
import {NavLink} from 'react-router-dom';


export default function HomePost({post}) {
	return (
		<div className="HomePost">
			<NavLink className="PostContainer"
			 		to={'/post'} 
					state={{pid: post._id}}>

				<h2 className="PostInfo">{post.title}- ${post.price} </h2>
				
				<div className="Pictures">
					{
						post.photos.map((picture) => {
							return <img key={Date.now()} src={picture} alt=":( unknown picture" />
						})
					}
				</div>
			</NavLink> 
			

			<div className="SellerApproveContainer">
				<NavLink
					to={'/profile'}
					state={{userId: post.seller._id}}>
					<h3 className="PostSeller">{post.seller.name}</h3>
				</NavLink>
					{post.approved != true ?
						<form action="http://localhost:3000/approve_post" method="post">
						<input type="hidden" name="id" value= {post._id} />
						<button className="EditButton">Approve</button>
						</form>: null
					}
			</div>
		</div>
	)
}
