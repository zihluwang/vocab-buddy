import {Nullable} from "@/types";

interface RegisterParams {
    username: string,
    password: string,
    region: number,
    phone: string,
    grade: number,
    email: Nullable<string>,
    userType: Nullable<number>
}

export default RegisterParams
