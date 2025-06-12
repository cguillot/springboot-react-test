import axios from 'axios';

export type InsurancePolicy = {
    id: string
    name: string
    status: "active" | "inactive"
    startDate: Date,
    endDate: Date,
    createdAt: Date,
    updatedAt: Date,
}

export type PolicySearchResponse = {
    data: InsurancePolicy[]
    dataCount: number
    totalCount: number
}

export type PolicyCreateCommand = {
    data: {
        name: string
        status: "active" | "inactive"
        startDate: string,
        endDate: string,
    }
}

export type PolicyCreateResponse = {
    data: InsurancePolicy
}

export type PolicyUpdateCommand = {
    data: {
        name: string
        status: "active" | "inactive"
        startDate: string,
        endDate: string,
    }
}

export type PolicyUpdateResponse = {
    data: InsurancePolicy
}

// TODO: should be provided through settings
const backendUrl = "api/v1";

function mapJsonPolicyToInsurancePolicy(jsonPolicy: any): InsurancePolicy | undefined {
    if (jsonPolicy) {
        return {
            id: jsonPolicy.id,
            name: jsonPolicy.name,
            status: jsonPolicy.status.toLowerCase() as "active" | "inactive",
            startDate: new Date(jsonPolicy.startDate),
            endDate: new Date(jsonPolicy.endDate),
            createdAt: new Date(jsonPolicy.createdAt),
            updatedAt: new Date(jsonPolicy.updatedAt),
        }
    }
}

export const fetchPolicies = async (pageIndex = 1, pageSize = 10): Promise<PolicySearchResponse> => {
    console.info('Fetching policies...')
    await new Promise((r) => setTimeout(r, 500))
    const params = {
        page: pageIndex,
        size: pageSize,
    };

    return axios
        .get<PolicySearchResponse>(backendUrl + '/policies', {params: params})
        .then((r) => {
            const data = r.data.data.map(mapJsonPolicyToInsurancePolicy);

            return {
                data: data,
                dataCount: r.data.dataCount,
                totalCount: r.data.totalCount,
            } as PolicySearchResponse;
        });
}

export const createPolicy = async (policy: PolicyCreateCommand) => {
    console.info('Creating policy...')
    await new Promise((r) => setTimeout(r, 500))
    return axios
        .post<PolicyCreateResponse>(backendUrl + '/policies', policy)
        .then((r) => {
            return {
                data: mapJsonPolicyToInsurancePolicy(r.data.data)
            };
        })
}

export const updatePolicy = async (policyId: string, policy: PolicyUpdateCommand) => {
    console.info(`Updating policy with id ${policyId}...`)
    await new Promise((r) => setTimeout(r, 500))
    return axios
        .put<PolicyUpdateResponse>(backendUrl + `/policies/${policyId}`, policy)
        .then((r) => {
            return {
                data: mapJsonPolicyToInsurancePolicy(r.data.data)
            };
        })
}

export const deletePolicy = async (policyId: string) => {
    console.info(`Deleting policy with id ${policyId}...`)
    await new Promise((r) => setTimeout(r, 500))
    return axios
        .delete(backendUrl + `/policies/${policyId}`)
}
