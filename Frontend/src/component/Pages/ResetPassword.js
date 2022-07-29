import React from 'react'
import { yupResolver } from '@hookform/resolvers/yup'
import { useParams } from 'react-router-dom'
import { useForm } from 'react-hook-form'
import ForgotPassword_Service from '../../services/ForgotPassword_Service'
import * as Yup from 'yup'

const ResetPassword = () => {

    const schema = Yup.object().shape({
        password: Yup.string().required("This field is required !!").min(6, "Password should be minimum 6 characters long").max(40, "Password should be maximum 40 characters long"),
        confirmPassword: Yup.string().required("This field is required").oneOf([Yup.ref("password"), null])
    })

    const { register, handleSubmit, formState: { errors } } = useForm({
        resolver: yupResolver(schema)
    })


    const { token } = useParams()
    const submitForm = ({ password, confirmPassword }) => {
        ForgotPassword_Service.resetPassword(token, password)
            .then((res) => {
                console.log(res)
            })
            .catch((error) => {
                console.log(error)
            })
    }

    return (
        <>
            <form className='container' onSubmit={handleSubmit(submitForm)}>
                <div className="form-group mb-4">
                    <label className='mb-2'>Password</label>
                    <input type="password" className="form-control" name="password" {...register('password')} placeholder="Enter Password" />
                    <p className='text-danger'> {errors.password?.message}</p>
                </div>
                <div className="form-group mb-4">
                    <label className='mb-2'>Confirm Password</label>
                    <input type="password" className="form-control" name='confirmPassword'  {...register('confirmPassword')} placeholder="Confirm Password" />
                    <p className='text-danger'> {errors.confirmPassword && "Passwords dont match"}</p>
                </div>
                <button type="submit" className="btn btn-primary">Submit</button>
            </form>
        </>
    )
}

export default ResetPassword