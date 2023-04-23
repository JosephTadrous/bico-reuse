import React from 'react';
import { useLocation, useNavigate } from 'react-router-dom';


export default function EditProfileForm() {
    const location= useLocation();
	const { state }= location;
	const user = state.user;


    const navigate = useNavigate();
    const clickHandler = () => {
        navigate("/");
    }

    const requestUrl = `http://localhost:3000/update?id=${user._id}`;

	return (
		<div className="EditForm"> 
            <form className="FormInside" action={requestUrl} method="post">
            <div className="FormName">Name: <input name="name" defaultValue={user.name}/> </div> <p> </p>
            <div className="FormCollege">College: <input name="college" defaultValue={user.college}/></div> <p> </p>
            <div className="FormEmail">Email: <input name="email" defaultValue={user.email}/></div> <p> </p>
            <div className="FormPhone">Phone Number: <input name="phone" defaultValue={user.phone}/></div> <p> </p>
						
						<div className="Buttons">
							<button onClick={clickHandler} className="EditButton" type="submit">Cancel</button>
							<input className="EditButton" type="submit" value="Submit form!"/>
						</div>
            </form>
        </div>
	)
}