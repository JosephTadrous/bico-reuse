import React from 'react';
import {useState, useEffect} from 'react';
import Post from './Post';
import { useLocation } from 'react-router-dom';
import './Post.css';

export default function PostPage() {
	let [post, setPost]= useState(null);

	const location= useLocation();
	const { state }= location;
	const pid= state.pid;

	// only fetches when the location changes
	useEffect(() => {
		fetch('http://localhost:3000/post?pid=' + pid)
		.then((res) => res.json())
		.then((post) => {
			setPost(post);
		})}, [location]);

	return (
		<div className="PostPage">
				
			<Routes>
				<Route exact path='/post' element={	<Post post={post}/> }></Route>
			</Routes>
		</div>
	)
}
