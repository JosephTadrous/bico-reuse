import React from 'react';
import { NavLink } from 'react-router-dom';
import PostUpdateForm from './PostUpdateForm';
import { useState } from 'react';



export default function Post({post}) {
	const [ currentPic, setCurrentPic ] = useState();

	function CreatePost() {
		// this means post is null or undefined
		if (!post) {
			return <></>
		} else { // generate the post component
			return <div className="Post">
					<div className="PostHeader">

						<div className="PostTitleAndDate">
							<h1>{post.title} - ${post.price}</h1>
							<h5>{(new Date(post.date)).toLocaleString()}</h5>
						</div>

						<div className="SellerInfo">
								<h5>Seller: 
									<NavLink
										to={'/profile'}
										state={{userId: post.seller._id}}>
										{" " + 	post.seller.name}
									</NavLink>	
								</h5>
							<h5>Email:  {post.seller.email}</h5>
							<h5>Phone:  {post.seller.phone}</h5>
						</div>
					</div>
					
					<div className="Pictures">
						{
							post.photos.map((picture) => {
								return <img key={Date.now()} src={picture} alt=":( unknown picture" />
							})
						}
					</div>
					<p className="Description">{post.description}</p>
					<div>
						{post.approved != true ?
							<form action="http://localhost:3000/approve_post" method="post">
							<input type="hidden" name="id" value= {post._id} />
							<button className="EditButton"> Approve</button>
							</form>: null
						}
					<div className="Description">
						<h3>Description:</h3>
						<p>{post.description}</p>
					</div>
					<form action="http://localhost:3000/delete_post" method="post">
						<input type="hidden" name="id" value= {post._id} />
						<input type="hidden" name="user_id" value= {post.seller._id} />
						<button className="DeleteButton">Delete</button>
						<NavLink className="CreateEditButton" to={'/editPost/' + post._id} state={{post: post}}>
							<button className="EditButton">Edit</button>
						</NavLink>	
					</form>
			</div>
		}
	}


	return (
		<div className="Post">
			{CreatePost()}
		</div>
	)
}
