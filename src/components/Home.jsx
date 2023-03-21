import React from 'react';
import { useState, useEffect } from 'react';
import HomePostList from './HomePostList';

export default function Home() {
	const [posts, setPosts] = useState([]);

	useEffect( () => {
		fetch('http://localhost:3000/api')
		.then((res) => res.json())
		.then((posts) => {
			setPosts(posts);
		});
	}, []);

	return (
		<div className="Home">
			<HomePostList posts={posts} />
		</div>
	)
}
