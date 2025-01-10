import {Nullable} from "@/types";

interface RegisterParams {
    username: string,
    password: string,
    region: number,
    phone: string,
    grade: number,
    email: Nullable<string>
}

export default RegisterParams