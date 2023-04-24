import React from 'react';
import HomePost from './HomePost';
import './Home.css'; 

export default function HomePostList({posts}) {
	return ( 
	<div>
		<ul className="PostLinks">
			{
				posts.map((post) => {
					return <li key={post._id}>
						<HomePost post={post}/>
					</li>
				})
			}
		</ul>
	</div>
	)
}
