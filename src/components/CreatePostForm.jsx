import React from 'react';

export default function CreatePostForm({post}) {
	return (
		<html> 
        <form action="http://localhost:3000/create_post" method="post" onsubmit='return false'>
        User ID: <input name="user_id"/> <p> </p>
        Title: <input name="title"/>  <p> </p>
        Description: <input name="description"/> <p> </p>
        Price: <input name="price"/> <p> </p>
        Image: <input name="photo"/> <p> </p>
        <input type="submit" value="Submit form!" href="http://localhost:5173"/>
        </form>
        </html>
	)
}
