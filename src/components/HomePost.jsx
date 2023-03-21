import React from 'react';


export default function HomePost({post}) {
	return (
		<div className="HomePost">
			<h1>{post.title} - ${post.price}</h1>
			<p>{post.seller.name}</p>
		</div>
	)
}
