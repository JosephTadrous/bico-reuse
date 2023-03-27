import React from 'react';
import { NavLink } from 'react-router-dom';
import PostUpdateForm from './PostUpdateForm';
import { Routes, Route } from 'react-router-dom';


export default function Post({post}) {

	function CreatePost() {
		// this means post is null or undefined
		if (!post) {
			return <></>
		} else { // generate the post component
			return <div className="Post">
					<div className="PostHeader">
						<h1>{post.title} - ${post.price}</h1>
						<h5>{(new Date(post.date)).toLocaleString()}</h5>

						<div className="SellerInfo">
							<h5>Seller: {post.seller.name}</h5>
							<h5>Email:  {post.seller.email}</h5>
							<h5>Phone:  {post.seller.phone}</h5>
						</div>

					</div>
					
					<div className="pictures">
						{
							post.photos.map((picture) => {
								return <img src={picture} alt="picture" />
							})
						}
					</div>
					<p className="Description">{post.description}</p>
			</div>
		}
	}


	return (
		<div className="Post">
			{CreatePost()}
		</div>
	)
}
