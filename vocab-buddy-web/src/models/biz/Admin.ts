import { Nullable, SupportedRegion, SupportedRegionCode } from "@/types"

interface Admin {
    id: Nullable<string>
    username: Nullable<string>
    email: Nullable<string>
    region: Nullable<SupportedRegion>
    regionCode: Nullable<SupportedRegionCode>
    phone: Nullable<string>
    adminType: Nullable<string>
    adminTypeCode: Nullable<number>
}

export default Admin
